import Component from 'metal-component';
import Soy from 'metal-soy';

import './field-options-toolbar.soy';
import templates from './sidebar.soy';

/**
 * SideBar Component
 */
class SideBar extends Component {}

// Register component
Soy.register(SideBar, templates, 'render');

export default SideBar;