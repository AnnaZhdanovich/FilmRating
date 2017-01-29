package by.zhdanovich.rat.service.factory;

import by.zhdanovich.rat.service.IAdminService;
import by.zhdanovich.rat.service.IClientService;
import by.zhdanovich.rat.service.ICommonService;
import by.zhdanovich.rat.service.impl.AdminServiceImpl;
import by.zhdanovich.rat.service.impl.ClientServiceImpl;
import by.zhdanovich.rat.service.impl.CommonServiceImpl;

/**
 * Class {@code ServiceFactory} is used to obtain a reference to instances of
 * Service classes. It creates only one instance of an object.
 * 
 * @author Anna
 *
 */
public class ServiceFactory {

	private static ServiceFactory FACTORY = new ServiceFactory();
	private final IClientService clientService = new ClientServiceImpl();
	private final ICommonService commonService = new CommonServiceImpl();
	private final IAdminService adminService = new AdminServiceImpl();

	private ServiceFactory() {
	}

	public static ServiceFactory getInstance() {
		return FACTORY;
	}

	public IClientService getClientService() {
		return clientService;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public IAdminService getAdminService() {
		return adminService;
	}

}
