import {useTranslation} from "react-i18next";
import styles from './Footer.module.css';

const Footer = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.footer}>
            <div className={styles.text}>
                <p>{t('footer.header')}</p>
                <div className={styles.panels}>
                    <div className={styles.options}>
                        <p>{t('footer.community')}</p>
                        <p>{t('footer.sign-in')}</p>
                        <p>{t('footer.sign-up')}</p>
                        <p>{t('footer.premium')}</p>
                    </div>
                    <div className={styles.options}>
                        <p>{t('footer.offers')}</p>
                        <p>{t('footer.stays')}</p>
                        <p>{t('footer.attractions')}</p>
                        <p>{t('footer.rating')}</p>
                    </div>
                    <div className={styles.options}>
                        <p>{t('footer.more')}</p>
                        <p>{t('footer.about')}</p>
                        <p>{t('footer.conditions')}</p>
                        <p>{t('footer.privacy')}</p>
                        <p>{t('footer.help')}</p>
                    </div>
                    <div className={styles.options}>
                        <p>{t('footer.contact')}</p>
                        <p>{t('footer.email')}</p>
                        <p>{t('footer.phone-number')}</p>
                    </div>
                </div>
                <p>{t('footer.copyright')}</p>
            </div>
        </div>
    );
}

export default Footer;