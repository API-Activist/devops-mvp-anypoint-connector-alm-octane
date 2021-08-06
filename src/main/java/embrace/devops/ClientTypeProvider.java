package embrace.devops;

import java.util.Set;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;
import org.mule.runtime.extension.api.values.ValueResolvingException;

public class ClientTypeProvider implements ValueProvider {

	@Override
	public Set<Value> resolve() throws ValueResolvingException {
		// TODO Auto-generated method stub
		return ValueBuilder.getValuesFor("HPE_MQM_UI","HPE_REST_API_TECH_PREVIEW", "ALM_OCTANE_TECH_PREVIEW", "IT_PRIVATE");
	}

}
