package uph.android.final_project_music_player;

public class UserModel {

        private String Username;
        private String Pass;
        private String Email;
        private Boolean Login;

        UserModel(String user, String pass, String email, Boolean login) {
            this.Username = user;
            this.Pass = pass;
            this.Email = email;
            this.Login = login;
        }

        public String getUsername() {
            return Username;
        }

        public String getPass() {
            return Pass;
        }

        public String getEmail() { return Email; }

        public boolean getLogin() { return Login; }

        public void setLogin(boolean login) {
            this.Login = login;
        }


}
