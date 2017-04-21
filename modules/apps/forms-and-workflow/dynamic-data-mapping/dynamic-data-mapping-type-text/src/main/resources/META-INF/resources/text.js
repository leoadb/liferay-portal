Liferay.Loader.require(
    'frontend-js-metal-web/metal-soy-bundle/build/bundle', 
    'dynamic-data-mapping-type-text/text.es', 
    function(MetalSoyBundle, Text) {
        if (!window.ddm) {
            window.ddm = {};
        }

        window.ddm['text'] = Text.default;

        AUI.add(
	        'liferay-ddm-text-soy',
	        function(A) {
                debugger;
            }
        );
    }
);