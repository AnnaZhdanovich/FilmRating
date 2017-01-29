package by.zhdanovich.rat.command.factory;

import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.impl.AddFilmCommand;
import by.zhdanovich.rat.command.impl.AddPersonalityCommand;
import by.zhdanovich.rat.command.impl.PreviousCommentsCommand;
import by.zhdanovich.rat.command.impl.DiscussedFilmCommand;
import by.zhdanovich.rat.command.impl.AssessmentsOfUserCommand;
import by.zhdanovich.rat.command.impl.AuthorisationCommand;
import by.zhdanovich.rat.command.impl.TakeDataOfUserCommand;
import by.zhdanovich.rat.command.impl.ChangeFilmCommand;
import by.zhdanovich.rat.command.impl.ChangeUserRoleCommand;
import by.zhdanovich.rat.command.impl.ChangeStatusCommentCommand;
import by.zhdanovich.rat.command.impl.CommentsOfUserCommand;
import by.zhdanovich.rat.command.impl.DeleteUserCommand;
import by.zhdanovich.rat.command.impl.FastSearchFilmCommand;
import by.zhdanovich.rat.command.impl.FindAddedCommentCommand;
import by.zhdanovich.rat.command.impl.FindAmountCommand;
import by.zhdanovich.rat.command.impl.FindFilmByGenreCommand;
import by.zhdanovich.rat.command.impl.FindFilmByIDCommand;
import by.zhdanovich.rat.command.impl.FindFilmMainCommand;
import by.zhdanovich.rat.command.impl.FindFilmsListCommand;
import by.zhdanovich.rat.command.impl.FindPersonalityCommand;
import by.zhdanovich.rat.command.impl.FindRemovedFilmsCommand;
import by.zhdanovich.rat.command.impl.FindUserCommand;
import by.zhdanovich.rat.command.impl.FindUserByLoginCommand;
import by.zhdanovich.rat.command.impl.FindUserRequestCommand;
import by.zhdanovich.rat.command.impl.FindUsersListCommand;
import by.zhdanovich.rat.command.impl.GiveAssessmentCommand;
import by.zhdanovich.rat.command.impl.GiveCommentCommand;
import by.zhdanovich.rat.command.impl.GiveRequestCommand;
import by.zhdanovich.rat.command.impl.LoadFileCommand;
import by.zhdanovich.rat.command.impl.LocalizationCommand;
import by.zhdanovich.rat.command.impl.LogoutCommand;
import by.zhdanovich.rat.command.impl.ChangeStatusRequestCommand;
import by.zhdanovich.rat.command.impl.NewFilmsCommand;
import by.zhdanovich.rat.command.impl.RatingFilmsCommand;
import by.zhdanovich.rat.command.impl.RatingUsersCommand;
import by.zhdanovich.rat.command.impl.RegistrationCommand;
import by.zhdanovich.rat.command.impl.RemoveFilmCommand;
import by.zhdanovich.rat.command.impl.SendToRegistrPageCommand;
import by.zhdanovich.rat.command.impl.SendToRequestPageCommand;
import by.zhdanovich.rat.command.impl.TakeCommentsFilmCommand;
import by.zhdanovich.rat.command.impl.UpdateDataOfFilmCommand;
import by.zhdanovich.rat.command.impl.UpdateDataOfUserCommand;
import by.zhdanovich.rat.command.impl.UpdateFilmDescriptionCommand;
import by.zhdanovich.rat.command.impl.UpdateFilmGenreCommand;
import by.zhdanovich.rat.command.impl.UpdateFilmPersonalityCommand;
import by.zhdanovich.rat.command.impl.UserBlockingCommand;

/**
 * Objects the enum as their fields contain references to objects of some
 * command.
 * 
 * @author Anna
 *
 */
public enum CommandType {

	AUTHORISATION {
		{
			this.command = new AuthorisationCommand();
		}
	},
	LOGOUT {
		{
			this.command = new LogoutCommand();
		}
	},
	LOCALIZATION {
		{
			this.command = new LocalizationCommand();
		}
	},

	REGISTRATION {
		{
			this.command = new RegistrationCommand();
		}
	},
	LOAD_FILE {
		{
			this.command = new LoadFileCommand();
		}
	},
	CHANGE {
		{
			this.command = new TakeDataOfUserCommand();
		}
	},
	FIND_USER {
		{
			this.command = new FindUserCommand();
		}
	},

	BLOCK_USER {
		{
			this.command = new UserBlockingCommand();
		}
	},

	UPDATE_USER {
		{
			this.command = new UpdateDataOfUserCommand();
		}
	},
	TAKE_ASSESSMENTS {
		{
			this.command = new AssessmentsOfUserCommand();
		}
	},
	TAKE_COMMENTS {
		{
			this.command = new CommentsOfUserCommand();
		}
	},
	ADD_ACTOR {
		{
			this.command = new AddPersonalityCommand();
		}
	},
	GET_PERSONALITY {
		{
			this.command = new FindPersonalityCommand();
		}
	},
	ADD_FILM {
		{
			this.command = new AddFilmCommand();
		}
	},
	FIND_FILM_BY_ID {
		{
			this.command = new FindFilmByIDCommand();
		}
	},
	FIND_FILMS_BY_GENRE {
		{
			this.command = new FindFilmByGenreCommand();
		}
	},
	FIND_LIST_OF_FILMS {
		{
			this.command = new FindFilmsListCommand();
		}
	},

	RATING_FILMS {
		{
			this.command = new RatingFilmsCommand();
		}
	},
	NEW_FILMS {
		{
			this.command = new NewFilmsCommand();
		}
	},
	ASIDE_FILM {
		{
			this.command = new DiscussedFilmCommand();
		}
	},
	ASIDE_FILM_COMMENT {
		{
			this.command = new PreviousCommentsCommand();
		}
	},
	GIVE_ASSESSMENT {
		{
			this.command = new GiveAssessmentCommand();
		}
	},
	GIVE_COMMENT {
		{
			this.command = new GiveCommentCommand();
		}
	},
	CHANGE_FILM {
		{
			this.command = new ChangeFilmCommand();
		}
	},
	REMOVE_FILM {
		{
			this.command = new RemoveFilmCommand();
		}
	},
	UPDATE_FILM_DATA {
		{
			this.command = new UpdateDataOfFilmCommand();
		}
	},
	UPDATE_FILM_PERSONALITY {
		{
			this.command = new UpdateFilmPersonalityCommand();
		}
	},
	UPDATE_GENRE {
		{
			this.command = new UpdateFilmGenreCommand();
		}
	},
	FAST_SEARCH {
		{
			this.command = new FastSearchFilmCommand();
		}
	},
	UPDATE_FILM_DESCRIPTION {
		{
			this.command = new UpdateFilmDescriptionCommand();
		}
	},
	FIND_FILM_MAIN {
		{
			this.command = new FindFilmMainCommand();
		}
	},
	RATING_USERS {
		{
			this.command = new RatingUsersCommand();
		}
	},
	FIND_ADDED_COMMENT {
		{
			this.command = new FindAddedCommentCommand();
		}
	},
	UNBLOCK_COMMENT {
		{
			this.command = new ChangeStatusCommentCommand();
		}
	},
	FIND_REMOVED_FILMS {
		{
			this.command = new FindRemovedFilmsCommand();
		}
	},
	FIND_USER_BY_LOGIN {
		{
			this.command = new FindUserByLoginCommand();
		}
	},
	GIVE_REQUEST {
		{
			this.command = new GiveRequestCommand();
		}
	},
	FIND_USER_REQUESTS {
		{
			this.command = new FindUserRequestCommand();
		}
	},

	MARKER_OF_REQUEST {
		{
			this.command = new ChangeStatusRequestCommand();
		}
	},

	SEND_REGISTRATION_PAGE {
		{
			this.command = new SendToRegistrPageCommand();
		}
	},

	SEND_REQUEST_PAGE {
		{
			this.command = new SendToRequestPageCommand();
		}
	},
	AMOUNT_FILMS_AND_USERS {
		{
			this.command = new FindAmountCommand();
		}
	},
	TAKE_COMMENTS_FILM {
		{
			this.command = new TakeCommentsFilmCommand();
		}
	},
	DELETE_USER {
		{
			this.command = new DeleteUserCommand();
		}
	},
	CHANGE_ROLE {
		{
			this.command = new ChangeUserRoleCommand();
		}
	},
	FIND_USERS_LIST {
		{
			this.command = new FindUsersListCommand();
		}
	};

	ICommand command;

	public ICommand getCurrentCommand() {
		return command;
	}

}
