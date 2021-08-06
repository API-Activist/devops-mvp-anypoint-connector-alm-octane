package embrace.devops;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.values.OfValues;

public class OctaneParameters {
	@Parameter
	@Expression(ExpressionSupport.SUPPORTED)
	@Optional(defaultValue = "1001")
	private String sharedspace;
	
	@Parameter
	@Expression(ExpressionSupport.SUPPORTED)
	@Optional(defaultValue = "1002")
	private String workspace;
	
	@Parameter
	@OfValues(TechPreviewProvider.class)
	private String techpreview;
	@Parameter
	@OfValues(ClientTypeProvider.class)
	private String clienttype;



	public String getTechpreview() {
		return techpreview;
	}

	public void setTechpreview(String techpreview) {
		this.techpreview = techpreview;
	}

	public String getClienttype() {
		return clienttype;
	}

	public void setClienttype(String clienttype) {
		this.clienttype = clienttype;
	}

	public String getSharedspace() {
		return sharedspace;
	}

	public void setSharedspace(String sharedspace) {
		this.sharedspace = sharedspace;
	}

	public String getWorkspace() {
		return workspace;
	}

	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}


}
