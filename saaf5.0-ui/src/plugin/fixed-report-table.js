function fixReportTable(container,fixColumn) {
  // Store references to table elements
  var thead = container.querySelector('thead');
  var tbody = container.querySelector('tbody');
  // Array.prototype.getNumArrayTotal = function(num){
  //   var total = this.reduce(function(pre,cur,index,arr){
  //     if(index>num-1){
  //       return pre+0;
  //     }
  //     return pre+cur.boundingWidth;
  //   },0);
  //   return total;
  // };

  function getNumArrayTotal  (currr,num){
    var total = currr.reduce(function(pre,cur,index,arr){
      if(index>num-1){
        return pre+0;
      }
      return pre+cur.boundingWidth;
    },0);
    return total;
  }
  // Style container
  container.style.overflow = 'auto';
  container.style.position = 'relative';

  // Add inline styles to fix the header row and leftmost column
  function relayout() {
    var ths = [].slice.call(thead.querySelectorAll('th'));
    var tbodyTrs = [].slice.call(tbody.querySelectorAll('tr'));

    /**
     * Remove inline styles so we resort to the default table layout algorithm
     * For thead, th, and td elements, don't remove the 'transform' styles applied
     * by the scroll event listener
     */
    tbody.setAttribute('style', '');
    thead.style.width = '';
    thead.style.position = '';
    thead.style.top = '';
    thead.style.left = '';
    thead.style.zIndex = '';
    ths.forEach(function(th) {
      th.style.display = '';
      th.style.width = '';
      th.style.position = '';
      th.style.top = '';
      th.style.left = '';
    });
    tbodyTrs.forEach(function(tr) {
      tr.setAttribute('style', '');
      //tr.style.height = '';
    });
    [].slice.call(tbody.querySelectorAll('td'))
      .forEach(function(td) {
        td.style.width = '';
        td.style.position = '';
        td.style.left = '';
      });

    /**
     * Store width and height of each th
     * getBoundingClientRect()'s dimensions include paddings and borders
     */
    var thStyles = ths.map(function(th) {
      var rect = th.getBoundingClientRect();
      var style = document.defaultView.getComputedStyle(th, '');
      return {
        boundingWidth: rect.width,
        boundingHeight: rect.height,
        width: parseInt(style.width, 10),
        paddingLeft: parseInt(style.paddingLeft, 10)
      };
    });

    // Set widths of thead and tbody
    var totalWidth = thStyles.reduce(function(sum, cur) {
      return sum + cur.boundingWidth;
    }, 0);
    tbody.style.display = 'block';
    tbody.style.width = totalWidth + 'px';
    // fixColumn 6

    var fixColumnArray = thStyles.slice(0,fixColumn);
    var fixColumnStartArray = JSON.parse(JSON.stringify(fixColumnArray));
    fixColumnArray.reverse() ;


    thead.style.width = totalWidth - getNumArrayTotal(fixColumnArray,fixColumn) + 'px';
    // Position thead
    thead.style.position = 'absolute';
    thead.style.top = '0';
    thead.style.left = getNumArrayTotal(fixColumnArray,fixColumn) + 'px';
    thead.style.zIndex = 11;
    var paramHeight = '';
    if(tbodyTrs.length>0){
      var paramHeight = tbodyTrs[0].querySelectorAll('td')[fixColumn].getBoundingClientRect().height;
    }

    // Set widths of the th elements in thead. For the fixed th, set its position
    ths.forEach(function(th, i) {
      th.style.width = thStyles[i].width + 'px';
      th.style.height = paramHeight + 'px';
      //th.style.height = thStyles[i].boundingHeight + 'px';
      if (i < fixColumn) {
        th.style.position = 'absolute';
        th.style.backgroundColor =  '#f5f6f8';
        th.style.top = '0';
        th.style.left = 0-getNumArrayTotal(fixColumnArray,fixColumn-i) + 'px';
      }
    });

    // Set margin-top for tbody - the fixed header is displayed in this margin
    tbody.style.marginTop = thStyles[0].boundingHeight + 'px';

    // Set widths of the td elements in tbody. For the fixed td, set its position

    tbodyTrs.forEach(function(tr, i) {
      tr.style.display = 'block';
      tr.style.paddingLeft = getNumArrayTotal(fixColumnArray,fixColumn) + 'px';
      tr.style.height = paramHeight + 'px' ;
     // tr.style.height = thStyles[i].boundingHeight + 'px';
      [].slice.call(tr.querySelectorAll('td'))
        .forEach(function(td, j) {
          td.style.width = thStyles[j].width + 'px';
          td.style.height =  paramHeight + 'px';
          if (j < fixColumn) {
            td.style.position = 'absolute';
            td.style.left = getNumArrayTotal(fixColumnStartArray,j) +'px';
            td.style.zIndex = 10;
          }
/*          if(j === fixColumn+1){
            var rectTd = td.getBoundingClientRect();

          }*/
        });
    });
  }

  // Initialize table styles
  relayout();

  // Update table cell dimensions on resize
  window.addEventListener('resize', resizeThrottler, false);
  var resizeTimeout;
  function resizeThrottler() {
    if (!resizeTimeout) {
      resizeTimeout = setTimeout(function() {
        resizeTimeout = null;
        relayout();
      }, 500);
    }
  }
  if( (container.scrollTop && container.scrollTop != 0 ) || (container.scrollLeft && container.scrollLeft != 0 ) ){
    thead.style.transform = 'translate3d(0,' + container.scrollTop + 'px,0)';
    var hTransform = 'translate3d(' + container.scrollLeft + 'px,0,0)';

    //thead.querySelector('th').style.transform = hTransform;
    [].slice.call(thead.querySelectorAll('th:nth-child(-n+'+fixColumn+')'))
        .forEach(function(th, i) {
          th.style.transform = hTransform;
        });
    [].slice.call(tbody.querySelectorAll('tr > td:nth-child(-n+'+fixColumn+')'))
        .forEach(function(td, i) {
          td.style.transform = hTransform;
          td.style.backgroundColor =  '#eee';
        });
  }
  // Fix thead and first column on scroll
  container.addEventListener('scroll', function() {
    thead.style.transform = 'translate3d(0,' + this.scrollTop + 'px,0)';
    var hTransform = 'translate3d(' + this.scrollLeft + 'px,0,0)';

    //thead.querySelector('th').style.transform = hTransform;
    [].slice.call(thead.querySelectorAll('th:nth-child(-n+'+fixColumn+')'))
        .forEach(function(th, i) {
          th.style.transform = hTransform;
        });
    [].slice.call(tbody.querySelectorAll('tr > td:nth-child(-n+'+fixColumn+')'))
      .forEach(function(td, i) {
        td.style.transform = hTransform;
        td.style.backgroundColor =  '#eee';
      });
  });

  /**
   * Return an object that exposes the relayout function so that we can
   * update the table when the number of columns or the content inside columns changes
   */
  return {
    relayout: relayout
  };
}
