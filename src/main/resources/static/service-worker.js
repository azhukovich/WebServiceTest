const CACHE_NAME = "sport-cache-v1";
const urlsToCache = [
    "/",
    "/favicon.svg",
    "/manifest.json",
    "/css/styles.css",
    "/js/app.js"
];

// Установка SW
self.addEventListener("install", event => {
    event.waitUntil(
        caches.open(CACHE_NAME).then(cache => cache.addAll(urlsToCache))
    );
});

// Перехват запросов
self.addEventListener("fetch", event => {
    event.respondWith(
        caches.match(event.request).then(response => {
            return response || fetch(event.request);
        })
    );
});
