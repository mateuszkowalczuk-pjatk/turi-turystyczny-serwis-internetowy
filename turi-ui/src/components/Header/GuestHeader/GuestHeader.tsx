import { useTranslation } from "react-i18next";
import Logo from '../../Logo'
import HeaderButtons from '../AuthButtons'
import styles from './GuestHeader.module.css'

const GuestHeader = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.header}>
            <Logo />
            <HeaderButtons
                firstButton={t('header.sign-in-button')}
                secondButton={t('header.sign-up-button')}
            />
        </div>
    )
}

export default GuestHeader;