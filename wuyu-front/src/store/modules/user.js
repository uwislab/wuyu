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
    return new Promise((resolve, reject) => {
      login({
        username: username.trim(),
        password: password,
        identityId: identityId,
      })
        .then((response) => {
          if (response.code === 200) {
            const { data } = response;
            commit("SET_TOKEN", data.token || "admin");
            commit("SET_LOGIN_USER", data);
            setToken(data.token || "admin");
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
      //调用api/user 里面的getInfo方法获取用户信息和权限信息
      // getInfo(state.token).then(response => {

      const data = {
        roles: ["admin"],
        introduction: "I am a super administrator",
        avatar:
          "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
        name: "Super Admin",
      };

      if (!data) {
        reject("验证失败，请重新登录。");
      }

      const { roles, name, avatar, introduction } = data;

      // roles must be a non-empty array
      // roles必须是一个数组
      if (!roles || roles.length <= 0) {
        reject("getInfo:角色必须是非空数组！");
      }
      //把roles存入到store
      commit("SET_ROLES", roles);
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
