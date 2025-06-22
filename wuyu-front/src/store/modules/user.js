import { login, logout, getInfo } from "@/api/user";
import { getToken, setToken, removeToken } from "@/utils/auth";
import router, { resetRouter } from "@/router";

const state = {
  userInfo: null,
  token: getToken(),
  name: "",
  avatar: "",
  introduction: "",
  roles: [],
  loginUser: {
    id: null,
    username: "",
    identity: null,
    gender: null,
    phone: "",
    realName: "",
    schoolId: null,
  },
};

const mutations = {
  SET_USER_INFO: (state, info) => {
    state.userInfo = info;
  },
  SET_TOKEN: (state, token) => {
    state.token = token;
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction;
  },
  SET_NAME: (state, name) => {
    state.name = name;
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar;
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles;
  },
  SET_LOGIN_USER: (state, userData) => {
    const { password, ...rest } = userData;
    state.loginUser = {
      ...state.loginUser,
      ...rest,
    };
  },
  CLEAR_LOGIN_USER: (state) => {
    state.loginUser = {
      id: null,
      username: "",
      identity: null,
      gender: null,
      phone: "",
      realName: "",
      schoolId: null,
    };
  },
};

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password, identityId } = userInfo;
    console.log('登录信息:', { username, identityId });
    return new Promise((resolve, reject) => {
      login({
        username: username.trim(),
        password: password,
        identityId: identityId,
      })
        .then((response) => {
          if (response.code === 200) {
            const { data } = response;
            console.log('登录成功，用户数据:', data);
            commit("SET_TOKEN", data.token || "admin");
            commit("SET_LOGIN_USER", data);
            // 确保用户数据被保存到localStorage，使用统一的键名
            localStorage.setItem('userInfo', JSON.stringify(data));
            setToken(data.token || "admin");
            
            // 保存用户名到localStorage，用于权限判断
            if (data.username) {
              localStorage.setItem('username', data.username);
              console.log('保存用户名到localStorage:', data.username);
            }
            if (data.realName) {
              localStorage.setItem('realName', data.realName);
              console.log('保存真实姓名到localStorage:', data.realName);
            }
            
            resolve(response);
          } else {
            reject(new Error(response.message || "登录失败"));
          }
        })
        .catch((error) => {
          reject(error);
        });
    });
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      console.log("state:", state)
      console.log("token状态:", state.token)
      
      // 检查loginUser是否有效，如果无效则尝试从localStorage恢复
      let currentUser = state.loginUser;
      console.log('初始loginUser状态:', currentUser);
      
      if (!currentUser || currentUser.id === null) {
        console.log('loginUser为空或ID为null，尝试从localStorage恢复用户信息');
        const savedUserInfo = localStorage.getItem('userInfo');
        console.log('localStorage中的userInfo:', savedUserInfo);
        
        if (savedUserInfo) {
          try {
            currentUser = JSON.parse(savedUserInfo);
            console.log('从localStorage恢复的用户信息:', currentUser);
            // 恢复到state中
            commit("SET_LOGIN_USER", currentUser);
            console.log('恢复后的loginUser:', state.loginUser);
          } catch (e) {
            console.error('解析localStorage中的用户信息失败:', e);
          }
        } else {
          console.log('localStorage中没有保存的用户信息');
          // 尝试从其他可能的键名获取
          const oldUserInfo = localStorage.getItem('UserInfo');
          if (oldUserInfo) {
            console.log('从旧键名UserInfo中找到用户数据');
            try {
              currentUser = JSON.parse(oldUserInfo);
              console.log('从旧键名恢复的用户信息:', currentUser);
              commit("SET_LOGIN_USER", currentUser);
              // 同时更新到新的键名
              localStorage.setItem('userInfo', oldUserInfo);
            } catch (e) {
              console.error('解析旧键名中的用户信息失败:', e);
            }
          }
        }
      }
      
      console.log('获取用户信息，当前loginUser:', currentUser);
      
      // 根据用户身份设置不同的角色
      let roles = [];
      if (currentUser && currentUser.identity !== null) {
        // 确保identity是数字类型
        const identity = parseInt(currentUser.identity, 10);
        console.log('用户身份ID转换为数字:', identity);
        
        // 0:校长 1：教务 2：班主任 3：老师
        switch (identity) {
          case 0:
            roles = ["0"]; // 校长
            break;
          case 1:
            roles = ["1"]; // 教务
            break;
          case 2:
            roles = ["2"]; // 班主任
            break;
          case 3:
            roles = ["3"]; // 老师
            break;
          default:
            // 如果是教务角色但identity值有问题，尝试从其他地方识别
            if (currentUser.realName && currentUser.realName.includes('教务')) {
              console.log('通过名称识别为教务角色');
              roles = ["1"]; 
            } else {
              roles = ["admin"];
            }
        }
      } else {
        // 紧急修复：如果当前是教务账号但没有正确识别，强制指定为教务角色
        // 这里可以根据实际情况修改判断条件
        const isJiaowuAccount = false; // 根据实际情况来判断是否为教务账号
        if (isJiaowuAccount) {
          roles = ["1"];
          console.log('强制设置为教务角色');
        } else {
          roles = ["admin"];
        }
      }

      console.log('用户身份ID:', currentUser?.identity, '分配的角色:', roles);

      const data = {
        roles: roles,
        introduction: "用户信息",
        avatar:
          "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
        name: currentUser ? currentUser.realName || "管理员" : "管理员",
      };

      if (!data) {
        reject("验证失败，请重新登录。");
      }

      const { roles: userRoles, name, avatar, introduction } = data;

      // roles must be a non-empty array
      // roles必须是一个数组
      if (!userRoles || userRoles.length <= 0) {
        reject("getInfo:角色必须是非空数组！");
      }
      //把roles存入到store
      commit("SET_ROLES", userRoles);
      commit("SET_NAME", name);
      commit("SET_AVATAR", avatar);
      commit("SET_INTRODUCTION", introduction);
      resolve(data);
    }).catch((error) => {
      alert("info catch");
      reject(error);
    });
    // })
  },

  // user logout
  logout({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      try {
        commit("SET_TOKEN", "");
        commit("SET_ROLES", []);
        commit("CLEAR_LOGIN_USER");
        removeToken();
        resetRouter();
        dispatch("tagsView/delAllViews", null, { root: true });
        resolve();
      } catch (error) {
        reject(error);
      }
    });
  },

  // remove token
  resetToken({ commit }) {
    return new Promise((resolve) => {
      commit("SET_TOKEN", "");
      commit("SET_ROLES", []);
      removeToken();
      resolve();
    });
  },

  // dynamically modify permissions
  async changeRoles({ commit, dispatch }, role) {
    const token = role + "-token";

    commit("SET_TOKEN", token);
    setToken(token);

    const { roles } = await dispatch("getInfo");

    resetRouter();

    // generate accessible routes map based on roles
    const accessRoutes = await dispatch("permission/generateRoutes", roles, {
      root: true,
    });
    // dynamically add accessible routes
    router.addRoutes(accessRoutes);

    // reset visited views and cached views
    dispatch("tagsView/delAllViews", null, { root: true });
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
