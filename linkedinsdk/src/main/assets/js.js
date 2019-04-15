/* Keep track of the last clicked element so we can un-highlight it */
var lastSelectedItem = null;

var showHighlighted = function(/* HTML element */view, /*boolean */highlighted) {
    if (view) {
        view.style.cssText = highlighted? '' : '';
    }
};

/* This new method, _addEventListener and _eventClick are the same as yours */
Object.prototype.each = function (fn, bind) {
                for (var i = 0; i < this.length; i++) {
                    if (i in this) {
                        fn.call(bind, this[i], i, this);
                    }
                }
            };

var _addListener = document.addEventListener || document.attachEvent,
    _eventClick = window.addEventListener ? 'click' : 'onclick';

/* I changed the element selection criteria, but you can change it back easily.
   I am adding event listeners all the leaf elements in the DOM. */
var elements = document.body.getElementsByTagName('*');
elements.each(function (el) {
    if (el.children.length == 0) {
        _addListener.call(el, _eventClick, function () {
            /* First, deal with the previously selected item*/
            showHighlighted(lastSelectedItem, false);
            if (lastSelectedItem !== null) {
                appHost.onClick(lastSelectedItem.outerHTML);                
            }

            /* Update the selected item reference */
            lastSelectedItem = el;


            /* Finally, deal with the previously selected item*/
            showHighlighted(lastSelectedItem, true);
            appHost.onClick(el.outerHTML);
        }, false);
    }
});
