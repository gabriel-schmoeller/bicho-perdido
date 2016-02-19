package bichoperdido.assyncservice.match.handlers;

import bichoperdido.appconfig.logger.AppLoggerFactory;
import bichoperdido.assyncservice.match.domain.Semelhanca;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Gabriel.
 */
@Component
public class AnuncioComparator {

    public static final String COMPARATOR_LOG = " -- -- [{}] Peso: {} - Grau: {}";
    public static final String SEMELHANCA_LOG = " -- -- Semelhança entre os anuncios é {}%";

    private final Logger matchLog = AppLoggerFactory.getMatch();

    public double compare(List<AtributoComparator> atributoComparators) {
        double grauTotal = 0;
        int pesoTotal = 0;
        List<Semelhanca> bonus = new ArrayList<>();

        for (AtributoComparator comparator : atributoComparators) {
            Semelhanca sem = comparator.compare();
            matchLog.debug(COMPARATOR_LOG, comparator.getClass().getSimpleName(), sem.getPeso(), sem.getGrau());

            switch (comparator.getAtributoTipo()) {
                case EXCLUSIVO:
                    if (sem.getGrau() == .0) return .0;
                case NORMAL:
                    grauTotal += sem.getGrau() * sem.getPeso();
                    pesoTotal += sem.getPeso();
                    break;
                case BONUS:
                    bonus.add(sem);
            }
        }

        Collections.sort(bonus, new Comparator<Semelhanca>() {
            @Override
            public int compare(Semelhanca o1, Semelhanca o2) {
                if (o1.getGrau() > o2.getGrau()) return 1;
                else if (o1.getGrau() < o2.getGrau()) return -1;
                else return 0;
            }
        });

        for (Semelhanca sem : bonus) {
            if (sem.getGrau() > (grauTotal/pesoTotal)) {
                grauTotal += sem.getGrau() * sem.getPeso();
                pesoTotal += sem.getPeso();
            }
        }

        double semelhancaTotal = grauTotal / pesoTotal;

        matchLog.debug(SEMELHANCA_LOG, semelhancaTotal * 100);

        return semelhancaTotal;
    }
}
