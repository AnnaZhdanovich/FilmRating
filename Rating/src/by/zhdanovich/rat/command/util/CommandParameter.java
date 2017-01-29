package by.zhdanovich.rat.command.util;

/**
 * Class {@code CommandParameter} contains the constants used on the level of
 * command.
 * 
 * @author Anna
 *
 */
public final class CommandParameter {
	
	public static final String NOT_ACT = "noAct";
	public static final String NAME = "name";
	public static final String TITLE = "title";
	public static final String COUNTRY = "country";
	public static final String ACTOR = "actor[]";
	public static final String PRODUCER = "producer[]";
	public static final String GENRE = "genre[]";
	public static final String YEAR = "year";
	public static final String FOLDER = "images";
	public static final String FILE = "file";
	public static final String DESCRIPTION = "description";
	public static final String MESSAGE = "message";
	public static final String FIRSTNAME = "firstname";
	public static final String LASTNAME = "lastname";
	public static final String ROLE = "role";
	public static final String UID_USER = "idUser";
	public static final int RECORDS_PER_PAGE = 5;
	public static final int PAGE_DEFAULT = 1;
	public static final String LOGIN = "login";
	public static final String PASSWORD = "password";
	public static final String PARAM_NAME_LOG = "log";
	public static final String PARAM_NAME_PASS = "pass";
	public static final String PARAM_NAME_LOGIN = "login";
	public static final String PARAM_NAME_PASSWORD = "pwd1";	
	public static final String PARAM_NAME_ID = "id";
	public static final String COMMENT_ID = "idComment";
	public static final String TEXT = "text";
	public static final String PAGE_GOAL = "pageParam";
	public static final String GENRES = "genre";
	public static final String GOAL = "goal";
	public static final String ID_FILM = "idFilm";
	public static final String NUMBER = "number";
	public static final String COMMENT = "comment";
	public static final String COMMENTS = "comments";
	public static final String TYPE = "type";
	public static final String FILM = "film";
	public static final String FILMS = "films";
	public static final String USERS = "users";
	public static final String USER = "user";
	public static final String FILM_UID = "filmUid";
	public static final String LOCALE = "locale";
	public static final String PARAM_NAME_FIRSTNAME = "firstname";
	public static final String PARAM_NAME_EMAIL = "email";
	public static final String ACTION = "act";
	public static final String GENRE_UID = "genre";
	public static final String PERSON_UID = "actor";
	public static final String STATUS = "status";
	public static final String MAIN = "main";
	public static final String REG_EXP_LOGIN = "[A-Za-z0-9\\_\\-\\s]{2,}";
	public static final String REG_EXP_PASSWORD = "[А-Яа-яЁёa-zA-Z0-9]{5,}";
	public static final String REG_EXP_NAME = "^[A-ZA-ЯЁ][A-ZA-ЯЁёа-яa-z0-9\\_\\-\\s]{2,}";
	public static final String REG_EXP_EMAIL = "^[^@]+@[^@.]+\\.[^@]+$";
	public static final String REG_EXP_TITLE = "^[A-ZА-ЯЁ0-9][A-ZА-Яa-zа-яЁё\\-\\s0-9\\.\\,\\!\\?]+";
	public static final String ADD_PAGE = "addPage";
	public static final String ASSIDE_COMMENTS = "asideComments";
	public static final String ASSIDE_FILMS = "asideFilms";
	public static final String ASSESSMENTS = "assessments";
	public static final String USER_CHANGES = "userChanges";	
	public static final String ACTORS = "actors";
	public static final String PRODUCERS = "producers";
	public static final String CHANGE_FILM = "changeFilm";
	public static final String SEARCH_FILM = "serchFilm";
	public static final String SEARCH_FILM_FASTY = "searchFilmFasty";
	public static final String FIND_BY_GENRE = "filmByGenre";
	public static final String INFO_FILM = "infoFilm";
	public static final String PERSONAL = "personal";
	public static final String ADDED_COMMENT = "addedComment";
	public static final String LIST_YSER = "listUser";
	public static final String IMAGES = "images";
	public static final String MESSAGE_UPDATE_USER = "messageUpdateUser";
	public static final String ERROR_UPDATE_MESSAGE = "errorUpdateMessage";
	public static final String NEW_FILM_LIST = "newFilmList";
	public static final String REGISTRATION = "registration";
	public static final String ACTOR1 = "actor";
	public static final String PRODUCER1 = "producer";
	public static final String GENRE1 = "genre";
	public static final String BLOKING_MESSAGE = "blokingMessage";
	public static final String REMOVED_FILM = "removedFilm";
	public static final String USER_BY_LOGIN = "userByLogin";
	public static final String LIST_REQUESTS = "requests";
	public static final String ID_REQUEST = "idRequest";
	public static final String GIVE_REQUEST = "giveRequest";
	public static final String MAIN_FILMS = "mainFilms";
	public static final String ERROR = "error";
	public static final String ERROR_GIVE_ASSESSMENT = "errorGiveAssessment";
	public static final String ERROR_DATA = "errorData";
	public static final String ERROR_ADD = "errorAdd";
	public static final String ERROR_SEARCH = "errorSerch";
	public static final String ERROR_STATUS_MESSAGE = "errorStatusMessage";
	public static final String ERROR_LOGIN_PASS_MESSAGE = "errorLoginPassMessage";
	public static final String ERROR_INPUT_DATA = "errorInputData";
	public static final String ADD_REQUEST = "addRequest";
	public static final String ERROR_AUTHORISATION_MESSAGE = "errorAuthorisationMessage";
	public static final String ERROR_FREE_LOGIN = "errorFreeLogin";
	public static final String GET = "GET";
	public static final String METHOD = "method";
	public static final String SEND_REDIRECT = "sendRedirect";
	public static final String FORWARD = "forward";
	public static final String PAGE = "page";
	public static final String NO_OF_PAGES = "noOfPages";
	public static final String CURRENT_PAGES = "currentPage";
	public static final String TARGET = "target";
	public static final String ADD_MESSAGE = "addMessage";
	public static final String IMAGE = "image";
	public static final String PATH_START_USER = "/jsp/startUser.jsp";
	public static final String PATH_MAIN = "/jsp/user/mainUser.jsp";
	public static final String AMOUNT_FILMS = "amountFilms";
	public static final String AMOUNT_USERS = "amountUsers";
	public static final String COMMENT_FILM = "commentFilm";
	public static final String ERROR_GIVE_COMMENT = "errorGiveComment";
	public static final String USERS_LIST = "usersList";
	public static final String COMMAND = "command";
	
	private CommandParameter(){
		
	}
}
