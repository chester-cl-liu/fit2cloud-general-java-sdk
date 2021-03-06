package com.fit2cloud.sdk;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fit2cloud.sdk.model.Application;
import com.fit2cloud.sdk.model.ApplicationDeployPolicyType;
import com.fit2cloud.sdk.model.ApplicationDeployment;
import com.fit2cloud.sdk.model.ApplicationDeploymentEventLog;
import com.fit2cloud.sdk.model.ApplicationDeploymentLog;
import com.fit2cloud.sdk.model.ApplicationRepo;
import com.fit2cloud.sdk.model.ApplicationRevision;
import com.fit2cloud.sdk.model.CloudCredential;
import com.fit2cloud.sdk.model.Cluster;
import com.fit2cloud.sdk.model.ClusterParam;
import com.fit2cloud.sdk.model.ClusterRole;
import com.fit2cloud.sdk.model.ClusterRoleAlertLogging;
import com.fit2cloud.sdk.model.CmdbVm;
import com.fit2cloud.sdk.model.ContactGroup;
import com.fit2cloud.sdk.model.Event;
import com.fit2cloud.sdk.model.GroupEnv;
import com.fit2cloud.sdk.model.KeyPair;
import com.fit2cloud.sdk.model.KeyPassword;
import com.fit2cloud.sdk.model.LaunchConfiguration;
import com.fit2cloud.sdk.model.Logging;
import com.fit2cloud.sdk.model.Metric;
import com.fit2cloud.sdk.model.MetricTop;
import com.fit2cloud.sdk.model.PortMonitor;
import com.fit2cloud.sdk.model.Script;
import com.fit2cloud.sdk.model.Server;
import com.fit2cloud.sdk.model.ServerMetric;
import com.fit2cloud.sdk.model.ServiceCatalogOrder;
import com.fit2cloud.sdk.model.Tag;
import com.fit2cloud.sdk.model.ViewScriptlog;
import com.google.gson.Gson;

public class Fit2CloudClientTest {
	
	private String apiConsumerKey;
	private String apiSecret;
	private String apiEndpoint;
	private Fit2CloudClient client;
	
	@Before
	public void setUp() throws Exception {
		this.apiConsumerKey = "MTUtOTYzMDAzY2U=";
		this.apiSecret = "0294541f-951f-4200-b134-f61de477380a";
		this.apiEndpoint = "http://localhost:6608/rest/";
		this.client = new Fit2CloudClient(apiConsumerKey,apiSecret,apiEndpoint);
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void getDeploymentEventLogsTset() throws Exception{
		List<ApplicationDeploymentEventLog> list = this.client.getDeploymentEventLogs((Long.parseLong(String.valueOf(230))));
		System.out.println(list.get(0));
		for (ApplicationDeploymentEventLog applicationDeploymentEventLog:list){
				System.out.println(applicationDeploymentEventLog.getEventName()+":"+applicationDeploymentEventLog.getMsg());
		}
	}
	@Test
	public void testGetClusters() throws Exception {
		System.out.println("List clusters...");
		List<Cluster> clusters = this.client.getClusters();
		assert clusters!=null && clusters.size()>0;
		System.out.println(clusters);
	}
	
	@Test
	public void testGetCluster() throws Exception {
		long clusterId = 2;
		Cluster cluster = this.client.getCluster(clusterId);
		System.out.println(cluster);
	}
	
	@Test
	public void testGetClusterRoles() throws Exception {
		long clusterId = 2;
		List<ClusterRole> clusterRoles = client.getClusterRoles(clusterId);
		assert clusterRoles!=null && clusterRoles.size()>0;
		System.out.println(clusterRoles);
	}
	
	@Test
	public void testGetServers() throws Exception {
		long clusterId = 2;
		long clusterRoleId = 2;
		String sort = "vm_id";
		String order = "desc";
		Integer pageSize = 3;
		Integer pageNum = 2;
		boolean showTerminated = true;
		List<Server> servers = client.getServers(clusterId, clusterRoleId, sort, order, pageSize, pageNum, showTerminated);
		assert servers!=null;
		System.out.println(servers);
	}
	
	@Test
	public void testGetServer() throws Exception {
		long serverId = 5;
		Server server = client.getServer(serverId);
		assert server!=null;
		System.out.println(server);
	}
	
	@Test
	public void testExecuteScriptInServer() throws Exception {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("#!/bin/bash").append("\n");
			sb.append("pwd").append("\n");
			sb.append("ls -l").append("\n");
			sb.append("date").append("\n");
			String scriptContent = sb.toString();
			long eventId = client.executeScript(446, scriptContent);
			System.out.println(eventId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetLoggingsByEventId() throws Exception {
		try {
			long eventId = 1l;
			List<Logging> logs = client.getLoggingsByEventId(eventId);
			System.out.println(new Gson().toJson(logs));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetEvent() throws Exception {
		try {
			long eventId = 86l;
			Event event = client.getEvent(eventId);
			System.out.println(new Gson().toJson(event));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSetClusterParam() throws Exception {
		try {
			long clusterId = 2;
			String paramName = "testParam";
			String paramValue = "testdata";
			boolean result = client.setClusterParam(clusterId, paramName, paramValue);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetClusterParams() throws Exception {
		try {
			long clusterId = 2;
			List<ClusterParam> result = client.getClusterParams(clusterId);
			System.out.println(new Gson().toJson(result));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetClusterParam() throws Exception {
		try {
			long clusterId = 2;
			String paramName = "testParam";
			ClusterParam result = client.getClusterParam(clusterId, paramName);
			System.out.println(new Gson().toJson(result));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testDeleteClusterParam() throws Exception {
		try {
			long clusterId = 2;
			String paramName = "testParam2";
			boolean result = client.deleteClusterParam(clusterId, paramName);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testLaunchServer() throws Exception {
		try {
			Server server = client.launchServer(2, 2, 17);
			System.out.println(server);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testLaunchServerAsync() throws Exception {
		try {
			Server server = client.launchServerAsync(2, 2, 37);
			System.out.println(server);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testTerminateServer() throws Exception {
		try {
			boolean result = client.terminateServer(193);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testStartServer() throws Exception {
		try {
			Server server = client.startServer(18);
			System.out.println(server);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testStopServer() throws Exception {
		try {
			boolean result = client.stopServer(18);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetScripts() throws Exception {
		try {
			List<Script> scripts = client.getScripts(null, null);
			System.out.println(new Gson().toJson(scripts));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetScript() throws Exception {
		try {
			long scriptId = 1l;
			Script script = client.getScript(scriptId);
			System.out.println(script);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testAddScript() throws Exception {
		try {
			String scriptText = "#!/bin/bash\r\n\r\n#列出系统进程\r\nps aux\r\n";
			String description = "script description";
			String name = "testScript";
			Long scriptId = client.addScript(name, description, scriptText);
			System.out.println(scriptId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testEditScript() throws Exception {
		try {
			String scriptText = "#!/bin/bash\r\n\r\n#列出系统进程[编辑]\r\nps aux\r\n";
			String description = "edit script description";
			long scriptId = 1l;
			boolean result = client.editScript(scriptId, description, scriptText);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testDeleteScript() throws Exception {
		try {
			long scriptId = 1l;
			boolean result = client.deleteScript(scriptId);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testGetLoggings() throws Exception {
		try {
			String order = "desc";
			String sort = "usedTime";
			Integer pageNum = null;
			Integer pageSize = null;
			String status = "failed";
			Long scriptId = null;
			Long serverId = null;
			Long clusterRoleId = null;
			Long clusterId = null;
			List<ViewScriptlog> loggings = client.getLoggings(clusterId, clusterRoleId, serverId, scriptId, status, pageSize, pageNum, sort, order);
			System.out.println(new Gson().toJson(loggings));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetTagList() throws Exception {
		try {
			Long serverId = null;
			Integer pageNum = null;
			Integer pageSize = null;
			String tagName = null;
			Long clusterId = null;
			Long clusterRoleId = null;
			List<Tag> tags = client.getTags(clusterId, clusterRoleId, serverId, tagName, pageSize, pageNum);
			System.out.println(new Gson().toJson(tags));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testSaveTag() throws Exception {
		try {
			String tagName = "myTestTag";
			String tagValue = "my test tag value";
			long serverId = 197l;
			Tag tag = client.saveTag(serverId, tagName, tagValue);
			System.out.println(new Gson().toJson(tag));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testDeleteTag() throws Exception {
		try {
			long serverId = 197l;
			String tagName = "myTestTag";
			boolean result = client.deleteTag(serverId, tagName);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetServersByTag() throws Exception {
		try {
			String tagName = "myTestTag";
			String tagValue = "my test tag value";
			Long clusterId = null;
			Long clusterRoleId = null;
			Integer pageSize = 0;
			Integer pageNum = 0;
			List<Server> servers = client.getServersByTag(clusterId, clusterRoleId, tagName, tagValue, pageSize, pageNum);
			System.out.println(new Gson().toJson(servers));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testAddApplicationRevision() throws Exception {
		try {
			String name = "v2.2";
			String description = "test desc";
			String location = "WordPressTest/wordpress-4.2-bin-201502151613-1.zip";
			String applicationName = "testapp";
			String repositoryName = "key-139-oss-wp";
			ApplicationRevision revision = client.addApplicationRevision(name, description, applicationName, repositoryName, location, null);
			System.out.println(new Gson().toJson(revision));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testAddDeployment() throws Exception {
		try {
			String description = "test desc";
			Long serverId = null;
			String deployPolicy = ApplicationDeployPolicyType.ONE_AT_A_TIME;
			String applicationName = "testapp";
			String applicationRevisionName = "v2.2";
			String clusterName = "cluster-1";
			String clusterRoleName = null;
			ApplicationDeployment deployment = client.addDeployment(applicationName, applicationRevisionName, clusterName, clusterRoleName, serverId, deployPolicy, description);
			System.out.println(new Gson().toJson(deployment));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetApplication() throws Exception {
		try {
			String applicationName = "testapp";
			Application app = client.getApplication(applicationName);
			System.out.println(new Gson().toJson(app));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetApplicationRepo() throws Exception {
		try {
			String applicationRepoName = "key-139-oss-wp";
			ApplicationRepo appRepo = client.getApplicationRepo(applicationRepoName);
			System.out.println(new Gson().toJson(appRepo));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetTopMetrics() throws Exception {
		try {
			List<Metric> metricList = client.getTopMetrics();
			System.out.println(new Gson().toJson(metricList));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetTopMetricData() throws Exception {
		try {
			List<MetricTop> metricDataList = client.getTopMetricData("cpu-usage", 3);
			System.out.println(new Gson().toJson(metricDataList));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetAlertLoggings() throws Exception {
		try {
			List<ClusterRoleAlertLogging> alertList = client.getAlertLoggings(null, null, null, "ERROR", "showNull", 3, 1);
			System.out.println(new Gson().toJson(alertList));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetCloudCredentials() throws Exception {
		try {
			List<CloudCredential> list = client.getCloudCredentials();
			System.out.println(new Gson().toJson(list));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetLaunchconfigurations() throws Exception {
		try {
			Long cloudCredentialId = 30l;
			List<LaunchConfiguration> list = client.getLaunchconfigurations(cloudCredentialId);
			System.out.println(new Gson().toJson(list));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetServerLoginInfos() throws Exception {
		try {
			Long serverId = 39l;
			KeyPassword result = client.getServerLoginInfo(serverId);
			System.out.println(new Gson().toJson(result));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetDeployments() throws Exception {
		List<ApplicationDeployment> list = client.getDeployments(7l, 4l, null, null);
		assert list!=null && list.size()>0;
		System.out.println(new Gson().toJson(list));
	}
	
	@Test
	public void testGetDeployment() throws Exception {
		ApplicationDeployment applicationDeployment = client.getDeployment(12l);
		assert applicationDeployment!=null;
		System.out.println(new Gson().toJson(applicationDeployment));
	}
	
	@Test
	public void testGetDeploymentLogs() throws Exception {
		List<ApplicationDeploymentLog> list = client.getDeploymentLogs(1l);
		assert list!=null && list.size()>0;
		System.out.println(new Gson().toJson(list));
	}
	
	@Test
	public void testGetServiceCatalogOrders() throws Exception {
		String sort = "created";
		String order = "desc";
		Integer pageSize = 3;
		Integer pageNum = 1;
		String status = "pending";
		List<ServiceCatalogOrder> orders = client.getServiceCatalogOrders(status, sort, order, pageSize, pageNum);
		assert orders!=null;
		System.out.println(new Gson().toJson(orders));
	}
	
	@Test
	public void testUpdateServiceCatalogOrder() throws Exception {
		String status = "rejectedd";
		long orderId = 32l;
		ServiceCatalogOrder result = client.updateServiceCatalogOrder(orderId, status);
		System.out.println(new Gson().toJson(result));
	}
	
	@Test
	public void testGetSupportedServerMetrics() throws Exception {
		Long clusterRoleId = 41l;
		List<KeyPair> result = client.getSupportedServerMetrics(clusterRoleId);
		System.out.println(new Gson().toJson(result));
	}
	
	@Test
	public void testGetServerMetrics() throws Exception {
		Long serverId = 490l;
		String metricName = "cpu-usage";
		Long endTime = System.currentTimeMillis()/1000;
		Long startTime = endTime - 60 * 60; // 1小时
		List<ServerMetric> result = client.getServerMetrics(serverId, metricName, startTime, endTime);
		System.out.println(new Gson().toJson(result));
	}
	
	@Test
	public void testGetPortMonitors() throws Exception {
		Integer pageSize = null;
		Integer pageNum = null;
		List<PortMonitor> result = client.getPortMonitors(pageSize, pageNum);
		System.out.println(new Gson().toJson(result));
	}
	
	@Test
	public void testGetPortMonitor() throws Exception {
		long portMonitorId = 2l;
		PortMonitor result = client.getPortMonitor(portMonitorId);
		System.out.println(new Gson().toJson(result));
	}
	
	@Test
	public void testGetServerSSHUrl() throws Exception {
		long serverId = 2l;
		String result = client.getServerSSHUrl(serverId);
		System.out.println(result);
	}
	
	@Test
	public void testGetContactGroup() throws Exception {
		long contactGroupId = 1l;
		ContactGroup result = client.getContactGroup(contactGroupId);
		System.out.println(new Gson().toJson(result));
	}
	
	@Test
	public void testGetGroupEnv() throws Exception {
		GroupEnv result = client.getGroupEnv();
		System.out.println(new Gson().toJson(result));
	}
	
	@Test
	public void registerServerTest() throws Exception {
		String sfServerId = "33c32057-4f38-46ab-9b7d-9648c2b47852";
		Long cloudServerId = 28948l;
		boolean installAgent = true;
		String user = "root";
		String password = null;
		String key = "testkey";
		Long port = 33l;
		Server server = client.registerServer(sfServerId, cloudServerId, installAgent, user , password, key, port);
		System.out.println(new Gson().toJson(server));
	}
	
	@Test
	public void registerCmdbServerTest() throws Exception {
		String sfServerId = "33c32057-4f38-46ab-9b7d-9648c2b47852";
		Long cmdbServerId = 5l;//cmdb_vm_id
		boolean installAgent = true;
		String user = "root";
		String password = null;
		String key = "testkey";
		Long port = 33l;
		CmdbVm server = client.registerCmdbServer(sfServerId, cmdbServerId, installAgent, user , password, key, port);
		System.out.println(new Gson().toJson(server));
	}
	
	@Test
	public void registerCmdbServer2Test() throws Exception {
		Long cmdbServerId = 5l;
		CmdbVm server = client.registerCmdbServer(cmdbServerId);
		System.out.println(new Gson().toJson(server));
	}
	
	@Test
	public void updateClusterTest() throws Exception {
		Long clusterId = 51l;
		String clusterName = "mes生产集群2";
		boolean result = client.updateCluster(clusterId, clusterName);
		System.out.println(new Gson().toJson(result));
	}
	
	@Test
	public void updateClusterRoleTest() throws Exception {
		Long clusterRoleId = 36l;
		String clusterRoleName = "mes应用2";
		boolean result = client.updateClusterRole(clusterRoleId, clusterRoleName);
		System.out.println(new Gson().toJson(result));
	}
	
	@Test
	public void changeClusterAndRoleTest() throws Exception {
		Long cmdbServerId = 5l;//cmdb_vm_id
		String clusterName = "testCluster";
		String clusterRoleName = "testClusterRole";
		String sshIp = "172.16.160.80";
		Long sshPort = 22l;
		String sshUser = "root";
		String sshPwd = "cloudpi";
		String osType = "linux";
		boolean result = client.changeClusterAndRole(cmdbServerId, clusterName, clusterRoleName , sshIp, sshPort, sshUser, sshPwd, osType);
		System.out.println(result);
	}
	
}
