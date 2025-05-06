if (typeof jQuery !== "undefined" && typeof saveAs !== "undefined") {
  (function ($) {
    $.fn.wordExport = function (fileName) {
      fileName = typeof fileName !== 'undefined' ? fileName : "jQuery-Word-Export";
      var static = {
        mhtml: {
          top:
            "Mime-Version: 1.0\nContent-Base: " +
            location.href +
            '\nContent-Type: Multipart/related; boundary="NEXT.ITEM-BOUNDARY";type="text/html"\n\n--NEXT.ITEM-BOUNDARY\nContent-Type: text/html; charset="utf-8"\nContent-Location: ' +
            location.href +
            "\n\n<!DOCTYPE html>\n" +
            '<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:w="urn:schemas-microsoft-com:office:word" xmlns:m="http://schemas.microsoft.com/office/2004/12/omml" xmlns="http://www.w3.org/TR/REC-html40">\n_html_</html>',
          head:
            '<head>\n<meta http-equiv="Content-Type" content="text/html; charset=utf-8">\n<style>\n_styles_\n</style>\n<!--[if gte mso 9]><xml><w:WordDocument><w:View>Print</w:View><w:TrackMoves>false</w:TrackMoves><w:TrackFormatting/><w:ValidateAgainstSchemas/><w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid><w:IgnoreMixedContent>false</w:IgnoreMixedContent><w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText><w:DoNotPromoteQF/><w:LidThemeOther>EN-US</w:LidThemeOther><w:LidThemeAsian>ZH-CN</w:LidThemeAsian><w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript><w:Compatibility><w:BreakWrappedTables/><w:SnapToGridInCell/><w:WrapTextWithPunct/><w:UseAsianBreakRules/><w:DontGrowAutofit/><w:SplitPgBreakAndParaMark/><w:DontVertAlignCellWithSp/><w:DontBreakConstrainedForcedTables/><w:DontVertAlignInTxbx/><w:Word11KerningPairs/><w:CachedColBalance/><w:UseFELayout/></w:Compatibility><w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel><m:mathPr><m:mathFont m:val="Cambria Math"/><m:brkBin m:val="before"/><m:brkBinSub m:val="--"/><m:smallFrac m:val="off"/><m:dispDef/><m:lMargin m:val="0"/> <m:rMargin m:val="0"/><m:defJc m:val="centerGroup"/><m:wrapIndent m:val="1440"/><m:intLim m:val="subSup"/><m:naryLim m:val="undOvr"/></m:mathPr></w:WordDocument></xml><![endif]--></head>\n',
          body: "<body>_body_</body>",
        },
      };
      var options = {
        maxWidth: 624,//最大宽度
      };
      // Clone selected element before manipulating it
      var markup = $(this).clone();

      // Remove hidden elements from the output
      markup.each(function () {
        var self = $(this);
        if (self.is(':hidden'))
          self.remove();
      });

      // Embed all images using Data URLs
      var images = Array();
      var img = markup.find('img');
      // var img = new Image(); 用这一行的话，WPS不显示图片，用上面的——只兼容office Word。
      var mhtmlBottom = "\n";
      for (var i = 0; i < img.length; i++) {
        // Calculate dimensions of output image
        var w = Math.min(img[i].width == 0 ? options.maxWidth : img[i].width, options.maxWidth);
        var h = (img[i].height == 0 ? options.defaultLength : img[i].height) * (w / (img[i].width == 0 ? options.maxWidth : img[i].width));

        // Create canvas for converting image to data URL
        var canvas = document.createElement("CANVAS");
        canvas.width = w;
        canvas.height = h;
        // Draw image to canvas
        var context = canvas.getContext('2d');
        context.drawImage(img[i], 0, 0, w, h);
        // Get data URL encoding of image
        var uri = canvas.toDataURL("image/png");
        // console.log(i+":"+uri);
        $(img[i]).attr("src", img[i].src);
        img[i].width = w;
        img[i].height = h;

        mhtmlBottom += "--NEXT.ITEM-BOUNDARY\n";
        mhtmlBottom += "Content-Location: " + uri + "\n";
        mhtmlBottom += "Content-Type: " + uri.substring(uri.indexOf(":") + 1, uri.indexOf(";")) + "\n";
        mhtmlBottom += "Content-Transfer-Encoding: " + uri.substring(uri.indexOf(";") + 1, uri.indexOf(",")) + "\n\n";
        mhtmlBottom += uri.substring(uri.indexOf(",") + 1) + "\n\n";
      }

      mhtmlBottom += "--NEXT.ITEM-BOUNDARY--";

      //TODO: load css from included stylesheet
      var styles = "";

      // Aggregate parts of the file together
      var fileContent = static.mhtml.top.replace("_html_", static.mhtml.head.replace("_styles_", styles) + static.mhtml.body.replace("_body_", markup.html())) + mhtmlBottom;

      // Create a Blob with the file contents
      var blob = new Blob([fileContent], {
        type: "application/msword;charset=utf-8"
      });
      saveAs(blob, fileName + ".doc");

    };
  })(jQuery);
} else {
  if (typeof jQuery === "undefined") {
    console.error("jQuery Word Export: missing dependency (jQuery)");
  }
  if (typeof saveAs === "undefined") {
    console.error("jQuery Word Export: missing dependency (FileSaver.js)");
  }
}
