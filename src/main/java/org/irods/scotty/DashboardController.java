package org.irods.scotty;

import java.io.Serializable;
import java.util.List;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.protovalues.UserTypeEnum;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.IRODSFileSystem;
import org.irods.jargon.core.pub.ResourceAO;
import org.irods.jargon.core.pub.UserAO;
import org.irods.jargon.core.pub.domain.Resource;
import org.irods.jargon.core.pub.domain.User;

public class DashboardController implements Serializable {

	private LoginController loginInfo;
	private Integer numberOfAdminUsers;
	private Integer numberOfRodsUsers;
	private Integer numberOfUsers;
	private Integer numberOfResources;
	private Integer numberOfResourcesInZone;
	private List<User> users;
	private List<Resource> resources;

	public DashboardController() {
		
	}
	
	public void setLoginInfo(LoginController bean) {
		this.loginInfo = bean;
	}
	public LoginController getLoginInfo() {
		return this.loginInfo;
	}
	
	public void setAllUsers() {
		Integer number = 0;
		IRODSAccessObjectFactory accessObjectFactory;
		
		IRODSAccount irodsAccount = loginInfo.getIRODSAccount();
		IRODSFileSystem irodsFileSystem = loginInfo.getIRODSFileSystem();
    	try {
    		accessObjectFactory = irodsFileSystem.getIRODSAccessObjectFactory();
			UserAO userAO = accessObjectFactory.getUserAO(irodsAccount);
			this.users = userAO.findAll(); 
    	} catch (JargonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			irodsFileSystem.closeAndEatExceptions();
		}
	}
	
	public Integer getNumberOfUsers() {
		Integer number = 0;
		
		if (this.users == null) {
			setAllUsers();
		}
		
		for (User user : this.users) {
			if ((user.getUserType().equals(UserTypeEnum.RODS_ADMIN))
				|| (user.getUserType().equals(UserTypeEnum.RODS_USER))) {
			// TODO: && (user.getZone().equals(getSelectedZone()))) { may want to do this or list separate zone numbers
				number++;
			}
		}
		return number;
	}

	public void setNumberOUsers(Integer numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}
	
	public Integer getNumberOfAdminUsers() {
		Integer number = 0;

		if (this.users == null) {
			getNumberOfUsers();
		}
		
		for (User user : this.users) {
			if ((user.getUserType().equals(UserTypeEnum.RODS_ADMIN))) {
				number++;
			}
		}
		return number;
	}

	public void setNumberOfAdminUsers(Integer numberOfAdminUsers) {
		this.numberOfAdminUsers = numberOfAdminUsers;
	}

	public Integer getNumberOfRodsUsers() {
		Integer number = 0;
		
		if (this.users == null) {
			getNumberOfUsers();
		}
		
		for (User user : this.users) {
			if ((user.getUserType().equals(UserTypeEnum.RODS_USER))) {
				number++;
			}
		}
		return number;
	}

	public void setNumberOfRodsUsers(Integer numberOfRodsUsers) {
		this.numberOfRodsUsers = numberOfRodsUsers;
	}
	
	public Integer getNumberOfResources() {
		Integer number = 0;
		
		IRODSAccessObjectFactory accessObjectFactory;
		
		IRODSAccount irodsAccount = loginInfo.getIRODSAccount();
		IRODSFileSystem irodsFileSystem = loginInfo.getIRODSFileSystem();
    	try {
    		accessObjectFactory = irodsFileSystem.getIRODSAccessObjectFactory();
			ResourceAO resourceAO = accessObjectFactory.getResourceAO(irodsAccount);
			number = resourceAO.findAll().size(); 
    	} catch (JargonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			irodsFileSystem.closeAndEatExceptions();
		}
		
		return number;
	}
	
	public void setNumberOfResources(Integer numberOfResources) {
		this.numberOfResources = numberOfResources;
	}
	
	public Integer getNumberOfResourcesInZone() {
		Integer number = 0;
		
		IRODSAccessObjectFactory accessObjectFactory;
		
		IRODSAccount irodsAccount = loginInfo.getIRODSAccount();
		IRODSFileSystem irodsFileSystem = loginInfo.getIRODSFileSystem();
    	try {
    		accessObjectFactory = irodsFileSystem.getIRODSAccessObjectFactory();
			ResourceAO resourceAO = accessObjectFactory.getResourceAO(irodsAccount);
			number = resourceAO.listResourcesInZone(loginInfo.getZone()).size();
    	} catch (JargonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			irodsFileSystem.closeAndEatExceptions();
		}
		
		return number;
	}
	
	public void setNumberOfResourcesInZone(Integer numberOfResourcesInZone) {
		this.numberOfResourcesInZone = numberOfResourcesInZone;
	}
}