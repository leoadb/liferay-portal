import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './field_options_toolbar.soy';

/**
 * FieldOptionsToolbar Component
 */
class FieldOptionsToolbar extends Component {}

// Register component
Soy.register(FieldOptionsToolbar, templates, 'render');

export default FieldOptionsToolbar;