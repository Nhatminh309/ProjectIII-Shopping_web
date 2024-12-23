var theme = {
    moneyFormat: "${{amount}}",
};

(function () {
    if ("sendBeacon" in navigator && "performance" in window) {
        var session_token = document.cookie.match(/_shopify_s=([^;]*)/);

        function handle_abandonment_event(e) {
            var entries = performance.getEntries().filter(function (entry) {
                return /monorail-edge.shopifysvc.com/.test(entry.name);
            });
            if (!window.abandonment_tracked && entries.length === 0) {
                window.abandonment_tracked = true;
                var currentMs = Date.now();
                var navigation_start = performance.timing.navigationStart;
                var payload = {
                    shop_id: 7162495060,
                    url: window.location.href,
                    navigation_start,
                    duration: currentMs - navigation_start,
                    session_token: session_token && session_token.length === 2 ? session_token[1] : "",
                    page_type: "index"
                };
                window.navigator.sendBeacon("https://monorail-edge.shopifysvc.com/v1/produce", JSON.stringify({
                    schema_id: "online_store_buyer_site_abandonment/1.1",
                    payload: payload,
                    metadata: {event_created_at_ms: currentMs, event_sent_at_ms: currentMs}
                }));
            }
        }

        window.addEventListener('pagehide', handle_abandonment_event);
    }
}());

(function initWebPixelsManager(config, callback) {
    const userAgent = navigator.userAgent;
    const isModern = /Chrome\/|Firefox\/|Edge\//.test(userAgent);
    const bundleTarget = isModern ? "modern" : "legacy";

    window.Shopify = window.Shopify || {};
    const analytics = window.Shopify.analytics = window.Shopify.analytics || {};
    analytics.replayQueue = [];

    analytics.publish = function(event, payload, options) {
        analytics.replayQueue.push([event, payload, options]);
    };

    // Tải script Web Pixels Manager
    const scriptUrl = `${config.baseUrl}/wpm/bundle/${bundleTarget}.js`;
    loadScript(scriptUrl, () => {
        const webPixelsManager = window.webPixelsManager.init(config);
        callback(webPixelsManager);

        analytics.replayQueue.forEach(([event, payload, options]) => {
            webPixelsManager.publishCustomEvent(event, payload, options);
        });
        analytics.replayQueue = [];
        analytics.publish = webPixelsManager.publishCustomEvent;
    });

    function loadScript(src, onLoad) {
        const script = document.createElement("script");
        script.src = src;
        script.async = true;
        script.onload = onLoad;
        script.onerror = () => console.error(`Failed to load script: ${src}`);
        document.head.appendChild(script);
    }
})({
    shopId: 7162495060,
    baseUrl: "https://extensions.shopifycdn.com/cdn/shopifycloud/web-pixels-manager",
    storefrontBaseUrl: "https://theme644-clothes-free.myshopify.com"
}, function (webPixelsManagerAPI) {
    webPixelsManagerAPI.publish("page_viewed", {});
});

// Shopify Analytics Meta
window.ShopifyAnalytics = window.ShopifyAnalytics || {};
window.ShopifyAnalytics.meta = window.ShopifyAnalytics.meta || {};
window.ShopifyAnalytics.meta.currency = 'USD';
window.ShopifyAnalytics.meta.page = { pageType: "home" };

// Trekkie Analytics
(function initTrekkie() {
    const trekkie = window.trekkie = window.trekkie || [];
    trekkie.methods = ['identify', 'page', 'track', 'trackForm', 'trackLink'];
    trekkie.methods.forEach(method => {
        trekkie[method] = function() {
            const args = Array.prototype.slice.call(arguments);
            args.unshift(method);
            trekkie.push(args);
        };
    });

    // Load Trekkie Script
    loadScript("//theme644-clothes-free.myshopify.com/cdn/s/trekkie.storefront.min.js");

    function loadScript(src) {
        const script = document.createElement("script");
        script.src = src;
        script.async = true;
        script.onerror = () => console.error(`Failed to load Trekkie script: ${src}`);
        document.head.appendChild(script);
    }
})();

document.addEventListener("DOMContentLoaded", function() {
    const categoryItem = document.getElementById("category-item");
    const submenu = document.getElementById("type");

    // Khi di chuột vào danh mục, hiển thị submenu ngay lập tức
    categoryItem.addEventListener("mouseenter", function() {
        submenu.style.display = "block";  // Hiển thị submenu
    });

    // Khi di chuột ra ngoài danh mục, ẩn submenu ngay lập tức
    categoryItem.addEventListener("mouseleave", function() {
        submenu.style.display = "none";  // Ẩn submenu
    });
});


