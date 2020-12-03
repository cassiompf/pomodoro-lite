package gmail.cassiompf.pomodorolite.enums;

public enum ActivityName {
    MAINACTIVITY(1), COUNTDOWNTIMER(2), RESTBREAK(3);

    private Integer valor;

    ActivityName(Integer valor) {
        this.valor = valor;
    }

    public Integer getValor() {
        return valor;
    }
}
