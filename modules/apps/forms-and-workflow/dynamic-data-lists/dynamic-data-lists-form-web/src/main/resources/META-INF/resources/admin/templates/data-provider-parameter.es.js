import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './data-provider-parameter.soy';

/**
 * DataProviderParameter Component
 */
class DataProviderParameter extends Component {}

// Register component
Soy.register(DataProviderParameter, templates, 'render');

export default DataProviderParameter;