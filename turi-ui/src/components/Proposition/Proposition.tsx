import {useTranslation} from "react-i18next";
import Stays from "./Stays";
import Attractions from "./Attractions";
import styles from './Proposition.module.css';

const Proposition = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.proposition}>
            <div className={styles.panels}>
                <div className={styles.stays}>
                    <p>{t('proposition.stay')}</p>
                    <Stays />
                </div>
                <div className={styles.attractions}>
                    <p>{t('proposition.attraction')}</p>
                    <Attractions />
                </div>
            </div>
        </div>
    )
}

export default Proposition;