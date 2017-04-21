Liferay.Loader.require(
    'frontend-js-metal-web/metal-soy-bundle/build/bundle', 
    'dynamic-data-mapping-type-select/select.es', 
    function(MetalSoyBundle, Select) {
        if (!window.ddm) {
            window.ddm = {};
        }

        window.ddm['select'] = Select.default;

        AUI.add(
	        'liferay-ddm-select-soy',
	        function(A) {
                debugger;
            }
        );
    }
);