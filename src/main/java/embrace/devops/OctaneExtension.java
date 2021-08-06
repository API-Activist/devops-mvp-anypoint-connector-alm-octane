package embrace.devops;

import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;


@Xml(prefix="almoctane")
@Extension(name="ALM Octane")
@Configurations(OctaneConfiguration.class)
public class OctaneExtension {
	

}
