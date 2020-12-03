package gmail.cassiompf.pomodorolite.enums;

public enum Task {

    POMODORO("POMODORO"), DESCANSO("DESCANSO");

    private String valor;

    Task(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
