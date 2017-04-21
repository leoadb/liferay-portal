Liferay.Loader.require(
    'frontend-js-metal-web/metal-soy-bundle/build/bundle', 
    'dynamic-data-mapping-type-checkbox/checkbox.es', 
    function(MetalSoyBundle, Checkbox) {
        if (!window.ddm) {
            window.ddm = {};
        }

        window.ddm['checkbox'] = Checkbox.default;

        AUI.add(
	        'liferay-ddm-checkbox-soy',
	        function(A) {
                debugger;
            }
        );
    }
);