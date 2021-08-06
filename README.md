# DevOps MVP Anypoint Connector ALM Octane 
This is a simple opensource ALM Octane Connector for Anypoint Studio for exchanging issues using API-led connectivity in interaction with other systems such as Atlassian Jira, ServiceNow, GitLab, BMC Remedy, etc. 
This ALM Octane MVP connector is build as a template for the #MuleSoft #Community to extend, reuse and share.
Implementation content is focused on main resources of ALM Octane. 

Use the ALM Octane REST API reference to extend this connector to your needs - [available here](https://admhelp.microfocus.com/octane/en/15.1.90/Online/Content/API/articles_API2.htm)

Recommendation is to use the Postman collection pack to extend this collection to a huge variety of operations - [available here](https://documenter.getpostman.com/view/13698513/TVmLCdts)

Watch the Tutorial Video for the Postman collection pack - [available here](https://youtu.be/qc_WnqgAzzo)

![Image of ALM Octane MuleSoft Connector](https://github.com/API-Activist/devops-mvp-anypoint-connector-alm-octane/blob/master/pictures/Octane.png)

## Getting started
This Anypoint Studio MVP (Minimum Viable Product) Connector for ALM Octane has been built for the MuleSoft Community as a template to reuse and if required further extend. 
The connector supports 21 operations in this MVP release with the focus on issues, which are:
- Add defects
- Add epics
- Add features
- Add manual tests
- Add requirement documents
- Add tasks
- Add test suites
- Add user stories
- Assign tests to test suites
- Get all defects
- Get all manual tests
- Get all requirements
- Get all tasks
- Get all tests
- Get all workitems
- Update defects
- Update features
- Update manual tests
- Update stories
- Update work Items

![Image of ALM Octane MuleSoft Connector](https://github.com/API-Activist/devops-mvp-anypoint-connector-alm-octane/blob/master/pictures/00_mule_palette.PNG)

## Installation of the MVP Connector for ALM Octane
This section describes the installation process for this mvp connector in order to use in Anypoint Studio. 

### Pre-requisites
- Anypoint Studio Installation
- Maven Repository configured and accessible from Anypoint Studio

### Step 1 - Download the MVP ALM Octane Connector
- Download Repository as ZIP
- Unpack it to a preferred location, typically into your Anypoint Studio workspaces area

### Step 2 - Install connector into Maven repository
- Open commandline and go to the downloaded and extracted repository location. 
- Perform "mvn install" 
- Connector should be installed successfully

![Image of ALM Octane MuleSoft Connector](https://github.com/API-Activist/devops-mvp-anypoint-connector-alm-octane/blob/master/pictures/02_mvn-install.PNG)

### Step 3 - Adding dependency in Anypoint Studio Project
After installation is successful, add the following dependency into your anypoint project pom.xml:

		<dependency>
			<classifier>mule-plugin</classifier>
			<groupId>embrace.devops.connectors</groupId>
			<artifactId>alm-octane-connector</artifactId>
			<version>0.1.9</version>
		</dependency>

The current version of this connector is 0.1.9. Once added, save the pom.xml file and your Mule Palette gets updated and you should see the ALM Octane connector.

### Step 4 - Create ALM Octane Configuration
Before you get started and consume the provided operations, make sure to configure the ALM Octane Connection within Anypoint Studio. 
- Protocol - http / https
- Host
- Port
- Client Id
- Client Secret

[Learn how to obtain client_id and client_secret for ALM Octane](https://admhelp.microfocus.com/octane/en/15.1.90/Online/Content/AdminGuide/how_setup_APIaccess.htm)


![Image of ALM Octane MuleSoft Connector](https://github.com/API-Activist/devops-mvp-anypoint-connector-alm-octane/blob/master/pictures/01_octane_config.PNG)

Now you are all set to use the ALM Octane Operations.

## Connector Operations - how to use
This section describes, how to use the provided operation for ALM Octane Connector.

The MVP version of the ALM Octane connectors has 3 main operations for all entities as an example. 
- **Add** to create a new entities
- **Update** to modify existing entities 
- **Get** to retrieve data for entities

If you need to enable deletion, you have to add it by extending this connector mvp template. The recommendation would be to leverage the postman collection pack to extend this connector to your needs.

**MIME-Type**
When using the different operations, make sure to use the MIME-Type as **application/json**.

### Operation specific properties
Each operation has additional properties to be added:
- shared_space - Id of the shared or isolated space
- workspace - Id of the workspace
- Techpreview - true or false - If you want to use the technical preview resources, set this option to true
- Clienttype - client type for accessing the ALM Octane data

![Image of ALM Octane MuleSoft Connector](https://github.com/API-Activist/devops-mvp-anypoint-connector-alm-octane/blob/master/pictures/02_operation_properties.PNG)


Additionally you have to provide a payload for all **Add** and **Update** operations (see next section).
![Image of ALM Octane MuleSoft Connector](https://github.com/API-Activist/devops-mvp-anypoint-connector-alm-octane/blob/master/pictures/03_json_meta_data.PNG)

### How to use the payload property
As ALM Octane payloads have couple of fields, it is recommended to use the Transform Message component before. 

When using a transform message component, make use of the example payloads provided for **new** and **update** operations in the meta-data-examples folder of this repository.

![Image of ALM Octane MuleSoft Connector](https://github.com/API-Activist/devops-mvp-anypoint-connector-alm-octane/blob/master/pictures/03_transform_data.PNG)

### Reponse of operations
By default it is a json sent back as string. Therefore it is required to set the MIME-Type on the operations to application/json. 

	{
	
			"total_count": 3,
			"data": [{
					"type": "defect",
					"creation_time": "2016-10-27T10:35:46Z",
					"parent": {
							"type": "work_item_root",
							"id": "1001"
					},
					"version_stamp": 9,
					"description": "<html><body>\n<p>Emptying out items in cart takes way too long. It could take more than 45 seconds. </p> \n</body></html>",
					"id": "1014",
					"severity": {
							"type": "list_node",
							"id": "1005"
					},
					"name": "Emptying out items in cart takes way too long",
					...
			},
			{
					"type": "defect",
					"creation_time": "2016-10-27T10:32:23Z",
					"parent": {
							"type": "feature",
							"id": "1007"
					},
					"version_stamp": 8,
					"description": "<html><body>\n<p>Shortcut key for deleting all items in cart does not work when there are more than 10 items in the cart.</p> \n</body></html>",
					"id": "1012",
					"severity": {
							"type": "list_node",
							"id": "1003"
					},
					"name": "Shortcut key for deleting all items in cart does not work.",
					...
			},
			{
					"type": "defect",
					"creation_time": "2016-10-27T10:33:03Z",
					"parent": {
							"type": "feature",
							"id": "1007"
					},
					"version_stamp": 9,
					"description": "<html><body>\n<p>When adding one item to cart, it appears as if the item was added two times. In actuality, it is only added once. If the user refreshes the screen, it appears correctly. This only happens in Chrome.</p> \n</body></html>",
					"id": "1013",
					"severity": {
							"type": "list_node",
							"id": "1005"
					},
					"name": "When adding one item to cart, it appears as if the item was added two times.",
					...
			}],
			"exceeds_total_count": false
	}

## Flow Example with ALM Octane operations
![Image of ALM Octane interaction](https://github.com/API-Activist/devops-mvp-anypoint-connector-alm-octane/blob/master/pictures/keep-issues-in-sync-gitlab-lead-flow.PNG)

	
## Video Tutorial
Link to the video tutorial: -to be provided soon-


## Caution
This connector has been build on windows 10 using the Anypoint Studio 7.10 IDE. It has only been tested with ALM Octane SaaS. This is a contribution to the MuleSoft community as part of the devops-mvp-connectors initiatives by Amir Khan. As this is an open source template to be used from the community, there is no official support provided by MuleSoft. Also if operations are missing, please use the ALM Octane API references to implement using the examples provided within this template.
	
ALM Octane API Reference: [available here](https://admhelp.microfocus.com/octane/en/15.1.90/Online/Content/API/articles_API2.htm)
	
### License agreement
By using this repository, you accept that Max the Mule is the coolest integrator on the planet - [Go to biography Max the Mule](https://brand.salesforce.com/content/characters-overview__3?tab=BogXMx2m)
