/**
*  Ajax Autocomplete for jQuery, version 1.1.5
*  (c) 2010 Tomas Kirda, Vytautas Pranskunas
*
*  Ajax Autocomplete for jQuery is freely distributable under the terms of an MIT-style license.
*  For details, see the web site: http://www.devbridge.com/projects/autocomplete/jquery/
*
*  Last Review: 07/24/2012
*/

/*jslint onevar: true, evil: true, nomen: true, eqeqeq: true, bitwise: true, regexp: true, newcap: true, immed: true */
/*global window: true, document: true, clearInterval: true, setInterval: true, jQuery: true */

(function ($) {

    var reEscape = new RegExp('(\\' + ['/', '.', '*', '+', '?', '|', '(', ')', '[', ']', '{', '}', '\\'].join('|\\') + ')', 'g');

    function fnFormatResult(value, data, currentValue) {
        var pattern = '(' + currentValue.replace(reEscape, '\\$1') + ')';
        return value.replace(new RegExp(pattern, 'gi'), '<strong>$1<\/strong>');
    }

    function Autocomplete(el, options) {
        this.el = $(el);
        this.el.attr('autocomplete', 'off');
        this.suggestions = [];
        this.data = [];
        this.badQueries = [];
        this.selectedIndex = -1;
        this.currentValue = this.el.val();
        this.intervalId = 0;
        this.cachedResponse = [];
        this.onChangeInterval = null;
        this.onChange = null;
        this.ignoreValueChange = false;
        this.serviceUrl = options.serviceUrl;
        this.isLocal = false;
        this.options = {
            hiddenId: null,
            mapping: { id: 'id', name: 'name' },
            autoSubmit: false,
            minChars: 1,
            maxHeight: 200,
            deferRequestBy: 0,
            width: 300,
            highlight: true,
            params: {},
            fnFormatResult: fnFormatResult,
            delimiter: null,
            zIndex: 9999,
            noCache: true,
            afterComplete: null
        };
        this.initialize();
        this.setOptions(options);
        this.el.data('autocomplete', this);
    }

    $.fn.autocomplete = function (options, optionName) {
        var autocompleteControl;

        if (typeof options == 'string') {
            autocompleteControl = this.data('autocomplete');
            if (typeof autocompleteControl[options] == 'function') {
                autocompleteControl[options](optionName);
            }
        } else {
            autocompleteControl = new Autocomplete(this.get(0) || $('<input />'), options);
        }
        return autocompleteControl;
    };

    Autocomplete.prototype = {
        killerFn: null,
        initialize: function () {

            var me, uid, autocompleteElId;
            me = this;
            uid = Math.floor(Math.random() * 0x100000).toString(16);
            autocompleteElId = 'Autocomplete_' + uid;

            this.killerFn = function (e) {
                if ($(e.target).parents('.autocomplete').size() === 0) {
                    me.killSuggestions();
                    me.disableKillerFn();
                }
            };

            if (!this.options.width) { this.options.width = this.el.width(); }
            this.mainContainerId = 'AutocompleteContainter_' + uid;

            $('<div id="' + this.mainContainerId + '" style="position:absolute;z-index:9999;"><div class="autocomplete-w1"><div class="autocomplete" id="' + autocompleteElId + '" style="display:none; width:300px;"></div></div></div>').appendTo('body');

            this.container = $('#' + autocompleteElId);
            this.fixPosition();
            if (window.opera) {
                this.el.keypress(function (e) { me.onKeyPress(e); });
            } else {
                this.el.keydown(function (e) { me.onKeyPress(e); });
            }
            this.el.keyup(function (e) { me.onKeyUp(e); });
            this.el.blur(function () { me.enableKillerFn(); });
            this.el.focus(function () { me.fixPosition(); });
            this.el.change(function () { me.onValueChanged(); });
        },
        extendOptions: function (options) {
            $.extend(this.options, options);
        },
        setOptions: function (options) {
            var o = this.options;
            this.extendOptions(options);
            if (o.lookup || o.isLocal) {
                this.isLocal = true;
                if ($.isArray(o.lookup)) { o.lookup = { suggestions: o.lookup, data: [] }; }
            }
            $('#' + this.mainContainerId).css({ zIndex: o.zIndex });
            this.container.css({ maxHeight: o.maxHeight + 'px', width: o.width, overflowX: 'hidden' });
        },
        clearCache: function () {
            this.cachedResponse = [];
            this.badQueries = [];
        },
        disable: function () {
            this.disabled = true;
        },
        enable: function () {
            this.disabled = false;
        },
        fixPosition: function () {
            var offset = this.el.offset();
            $('#' + this.mainContainerId).css({ top: (offset.top + this.el.innerHeight()) + 'px', left: offset.left + 'px' });
        },
        enableKillerFn: function () {
            var me = this;
            $(document).bind('click', me.killerFn);
        },
        disableKillerFn: function () {
            var me = this;
            $(document).unbind('click', me.killerFn);
        },
        killSuggestions: function () {
            var me = this;
            this.stopKillSuggestions();
            this.intervalId = window.setInterval(function () { me.hide(); me.stopKillSuggestions(); }, 300);
        },
        stopKillSuggestions: function () {
            window.clearInterval(this.intervalId);
        },
        onValueChanged: function () {
            this.change(this.selectedIndex);
        },
        onKeyPress: function (e) {
            if (this.disabled || !this.enabled) { return; }
            // return will exit the function
            // and event will not be prevented
            switch (e.keyCode) {
                case 27: //KEY_ESC:
                    this.el.val(this.currentValue);
                    this.hide();
                    break;
                case 9: //KEY_TAB:
                case 13: //KEY_RETURN:
                    if (this.selectedIndex === -1) {
                        this.hide();
                        return;
                    }
                    this.select(this.selectedIndex);
                    if (e.keyCode === 9) { return; }
                    break;
                case 38: //KEY_UP:
                    this.moveUp();
                    break;
                case 40: //KEY_DOWN:
                    this.moveDown();
                    break;
                default:
                    return;
            }
            e.stopImmediatePropagation();
            e.preventDefault();
        },
        onKeyUp: function (e) {
            if (this.disabled) { return; }
            switch (e.keyCode) {
                case 38: //KEY_UP:
                case 40: //KEY_DOWN:
                    return;
            }
            clearInterval(this.onChangeInterval);
            if (this.currentValue !== this.el.val()) {
                if (this.options.deferRequestBy > 0) {
                    // Defer lookup in case when value changes very quickly:
                    var me = this;
                    this.onChangeInterval = setInterval(function () { me.onValueChange(); }, this.options.deferRequestBy);
                } else {
                    this.onValueChange();
                }
            }
            this.setHidden(-1);
        },
        onValueChange: function () {
            clearInterval(this.onChangeInterval);
            this.currentValue = this.el.val();
            var q = this.getQuery(this.currentValue);
            this.selectedIndex = -1;
            /*if (this.ignoreValueChange) {
            this.ignoreValueChange = false;
            return;
            }*/
            if (q === '' || q.length < this.options.minChars) {
                this.hide();
            } else {
                this.getSuggestions(q);
                this.removeHidden();
            }
        },
        getQuery: function (val) {
            var d, arr;
            d = this.options.delimiter;
            if (!d) { return $.trim(val); }
            arr = val.split(d);
            return $.trim(arr[arr.length - 1]);
        },
        getSuggestionsLocal: function (q) {
            var ret, arr, len, val, i;
            arr = this.options.lookup;
            len = arr.suggestions.length;
            ret = { suggestions: [], data: [] };
            q = q.toLowerCase();
            for (i = 0; i < len; i++) {
                val = arr.suggestions[i];
                if (val.toLowerCase().indexOf(q) === 0) {
                    ret.suggestions.push(val);
                    ret.data.push(arr.data[i]);
                }
            }
            return ret;
        },
        getSuggestions: function (q) {

            var op, me;
            /* ���û��� cr = this.isLocal ? this.getSuggestionsLocal(q) : this.cachedResponse[q]; //dadeta this.options.isLocal ||
            if (cr && $.isArray(cr.suggestions)) {
            this.suggestions = cr.suggestions;
            this.data = cr.data;
            this.suggest();
            } else*/
            if (!this.isBadQuery(q)) {
                me = this;
                me.options.params.query = q;
                op = me.options.optionalParams;
                if (typeof op !== 'undefined') {
                    for (var item in op) {
                        if (typeof me.options.domContext !== 'undefined') {
                            me.options.params[item] = me.options.domContext.find(op[item]).val();
                        }
                        else {
                            me.options.params[item] = $(op[item]).val();
                        }
                    }
                }
                $.post(this.serviceUrl, me.options.params, function (txt) { me.processResponse(txt); }, 'text');
            }
        },
        isBadQuery: function (q) {
            var i = this.badQueries.length;
            while (i--) {
                if (q.indexOf(this.badQueries[i]) === 0) { return true; }
            }
            return false;
        },
        hide: function () {
            this.enabled = false;
            this.selectedIndex = -1;
            this.container.hide();
        },
        suggest: function () {

            if (this.suggestions.length === 0) {
                this.hide();
                return;
            }

            var me, len, div, f, v, i, s, mOver, mClick;
            me = this;
            len = this.suggestions.length;
            f = this.options.fnFormatResult;
            v = this.getQuery(this.currentValue);
            mOver = function (xi) { return function () { me.activate(xi); }; };
            mClick = function (xi) { return function () { me.select(xi); }; };
            this.container.hide().empty();
            for (i = 0; i < len; i++) {
                s = this.suggestions[i];
                div = $((me.selectedIndex === i ? '<div class="selected"' : '<div') + ' title="' + s + '">' + f(s, this.data[i], v) + '</div>');
                div.mouseover(mOver(i));
                div.click(mClick(i));
                this.container.append(div);
            }
            this.enabled = true;
            //fix the bug when the mouse leave out the container, no item should be selected
            this.container.mouseleave(function () {
                me.deactivate();
            });
            this.container.show();
        },
        processResponse: function (text) {
            var response;
            try {
                response = eval('(' + text + ')');
            }
            catch (err) {
                return;
            }
            if (!$.isArray(response.data)) { response.data = []; }
            if (!this.options.noCache) {
                this.cachedResponse[response.query] = response;
                if (response.suggestions.length === 0) { this.badQueries.push(response.query); }
            }
            if (response.query === this.getQuery(this.currentValue)) {
                this.suggestions = response.suggestions;
                this.data = response.data;
                this.suggest();
                if (this.options.afterComplete != null && util.isFunction(this.options.afterComplete)) {
                    this.options.afterComplete();
                }
            }
        },
        activate: function (index) {
            var divs, activeItem;
            divs = this.container.children();
            // Clear previous selection:
            if (this.selectedIndex !== -1 && divs.length > this.selectedIndex) {
                $(divs.get(this.selectedIndex)).removeClass();
            }
            this.selectedIndex = index;
            if (this.selectedIndex !== -1 && divs.length > this.selectedIndex) {
                activeItem = divs.get(this.selectedIndex);
                $(activeItem).addClass('selected');
            }
            return activeItem;
        },
        deactivate: function () {
            var divs = this.container.children();
            if (this.selectedIndex !== -1 && divs.length > this.selectedIndex) {
                $(divs.get(this.selectedIndex)).removeClass();
            }
            this.selectedIndex = -1;
        },
        removeHidden: function () {
            var value = this.el.val();
            if (this.options.hiddenId != null) {
                var temp = this.el.parent().find('input[name="' + this.options.hiddenId + '"]');
                if (typeof temp !== 'undefined') {
                    temp.each(function (index, item) {
                        $(item).remove();
                    });
                }
            }
        },
        setHidden: function (i) {
            var value = this.el.val(),
            id = this.options.hiddenId,
            mapping = this.options.mapping;
            for (var index = 0; index < this.data.length; index++) {
                if (String(this.data[index][mapping.name]) === value) {
                    i = index;
                    break;
                }
            }
            if (id != null) {
                var temp = this.el.parent().find('input[name="' + id + '"]');
                if (typeof temp !== 'undefined') {
                    temp.each(function (index, item) {
                        $(item).remove();
                    });
                }
                var d = this.data[i];
                if (util.isNull(d)) {
                    return false;
                }
                var html = util.formatString('<input type="hidden" name="{0}" value="{1}" />', id, d[mapping.id]);
                this.el.after(html);
                if (!util.isNull(this.options.onSetHidden)) {
                    this.options.onSetHidden(d[mapping.id]);
                }
            }
            return true;
        },
        setEmptyHidden: function () {
            var isMultiple = this.options.isMultiple,
            id = this.options.hiddenId;
            if (!isMultiple) { return; }
            if (id != null) {
                var html = util.formatString('<input type="hidden" name="{0}" value="00000000-0000-0000-0000-000000000000" />', id);
                this.el.after(html);
            }
        },
        select: function (i) {
            var selectedValue, f;
            selectedValue = this.suggestions[i];
            if (selectedValue) {
                this.el.val(selectedValue);
                if (this.options.autoSubmit) {
                    f = this.el.parents('form');
                    if (f.length > 0) { f.get(0).submit(); }
                }
                this.ignoreValueChange = true;
                this.hide();
                this.onSelect(i);
            }
        },
        change: function (i) {
            var selectedValue, fn, me;
            me = this;
            selectedValue = this.suggestions[i];
            if (selectedValue) {
                var s, d;
                s = me.suggestions[i];
                d = me.data[i];
                me.el.val(me.getValue(s));
            }
            else {
                s = '';
                d = -1;
                var flag = this.setHidden(-1);
                if (!flag) {
                    this.setEmptyHidden();
                }
            }
            fn = me.options.onChange;
            if ($.isFunction(fn)) { fn(s, d, me.el); }
        },
        moveUp: function () {
            if (this.selectedIndex === -1) { return; }
            if (this.selectedIndex === 0) {
                this.container.children().get(0).className = '';
                this.selectedIndex = -1;
                this.el.val(this.currentValue);
                return;
            }
            this.adjustScroll(this.selectedIndex - 1);
        },
        moveDown: function () {
            if (this.selectedIndex === (this.suggestions.length - 1)) { return; }
            this.adjustScroll(this.selectedIndex + 1);
        },
        adjustScroll: function (i) {
            var activeItem, offsetTop, upperBound, lowerBound;
            activeItem = this.activate(i);
            offsetTop = activeItem.offsetTop;
            upperBound = this.container.scrollTop();
            lowerBound = upperBound + this.options.maxHeight - 25;
            if (offsetTop < upperBound) {
                this.container.scrollTop(offsetTop);
            } else if (offsetTop > lowerBound) {
                this.container.scrollTop(offsetTop - this.options.maxHeight + 25);
            }
            this.el.val(this.getValue(this.suggestions[i]));
        },
        onSelect: function (i) {
            var me, fn, s, d, id, mapping;
            me = this;
            fn = me.options.onSelect;
            s = me.suggestions[i];
            d = me.data[i];
            me.el.val(me.getValue(s));
            this.setHidden(i);
            if ($.isFunction(fn)) { fn(s, d, me.el, id); }
        },
        getValue: function (value) {
            var del, currVal, arr, me;
            me = this;
            del = me.options.delimiter;
            if (!del) {

                return value;
            }
            currVal = me.currentValue;
            arr = currVal.split(del);
            if (arr.length === 1) { return value; }
            return currVal.substr(0, currVal.length - arr[arr.length - 1].length) + value;
        }
    };

} (jQuery));
