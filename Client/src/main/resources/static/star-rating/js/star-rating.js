(function (factory) {
    'use strict';
    if (typeof define === 'function' && define.amd) {
        define(['jquery'], factory);
    } else if (typeof module === 'object' && typeof module.exports === 'object') { 
        factory(require('jquery'));
    } else { 
        factory(window.jQuery);
    }
}(function ($) {
    "use strict";

    if (!$.fn.ratingThemes) {
        $.fn.ratingThemes = {};
    }

    $.fn.ratingThemes['krajee-svg'] = {
        filledStar: '<span class="krajee-icon krajee-icon-star"></span>',
        emptyStar: '<span class="krajee-icon krajee-icon-star"></span>',
        clearButton: '<span class="krajee-icon-clear"></span>'
    };
}));