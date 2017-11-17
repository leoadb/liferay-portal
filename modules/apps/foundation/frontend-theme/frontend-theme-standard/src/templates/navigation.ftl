<#if has_navigation && is_setup_complete>
	<button aria-controls="navigationCollapse" aria-expanded="false" aria-label="Toggle navigation"  class="navbar-toggler navbar-toggler-right" data-target="#navigationCollapse" data-toggle="collapse" type="button">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div aria-expanded="false" class="collapse mt-4 mt-md-0 navbar-collapse text-uppercase" id="navigationCollapse">
		<@liferay.navigation_menu default_preferences="${preferences}" />
	</div>
</#if>