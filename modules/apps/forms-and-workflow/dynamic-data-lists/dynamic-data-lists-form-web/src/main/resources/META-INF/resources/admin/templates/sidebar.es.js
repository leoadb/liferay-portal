import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './sidebar.soy';
import toolbarTemplates from './field_options_toolbar.soy';

/**
 * SideBar Component
 */
class SideBar extends Component {}

// Register component
Soy.register(SideBar, templates, 'render');

export default SideBar;