class BasePage {
    public BasePage() {
        System.out.println("Base Page created");
    }
}

class LoginPage extends BasePage {
    public LoginPage() {

        System.out.println("Login Page created");
    }
}

class Test {
    public static void main(String[] args) {
        BasePage basePage = new LoginPage();

        LoginPage loginPage = new LoginPage();
    }
}