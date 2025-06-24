//在项目的util文件夹下新建htmlToPdf.js

import html2Canvas from "html2canvas";
import JsPDF from "jspdf";
import { Loading } from "element-ui"; //此处根据你实际使用的框架的loading进行引入
let loading;
export default {
  install(Vue, options) {
    Vue.prototype.getPdf = function(pdfTitle) {
      loading = Loading.service({
        lock: true,
        text: "正在下载pdf...",
        background: "rgba(0,500,200,.5)"
      });
      html2Canvas(document.querySelector("#pdfDom"), {
        allowTaint: true,
        useCORS: true
      }).then(function(canvas) {
        loading.close();
        let contentWidth = canvas.width;
        let contentHeight = canvas.height;
        let pageHeight = (contentWidth / 592.28) * 841.89;
        let leftHeight = contentHeight;
        let position = 0;
        let imgWidth = 595.28;
        let imgHeight = (592.28 / contentWidth) * contentHeight;
        let pageData = canvas.toDataURL("image/jpeg", 1.0);
        let PDF = new JsPDF("", "pt", "a4");
        if (leftHeight < pageHeight) {
          PDF.addImage(pageData, "JPEG", 0, 0, imgWidth, imgHeight);
        } else {
          while (leftHeight > 0) {
            PDF.addImage(pageData, "JPEG", 0, position, imgWidth, imgHeight);
            leftHeight -= pageHeight;
            position -= 841.89;
            if (leftHeight > 0) {
              PDF.addPage();
            }
          }
        }
        PDF.save(pdfTitle + ".pdf");
      });
    };
  }
};

//在需要导入的DOM节点上设置  id="pdfDom"   直接写入点击事件@click="getPdf('此处传入导入的文件名称')"
