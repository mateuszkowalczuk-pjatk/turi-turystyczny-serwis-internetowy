import { useTranslation } from "react-i18next";
import Logo from '../../Logo'
import HeaderButtons from '../HeaderButtons'
import styles from './GuestHeader.module.css'

const GuestHeader = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.header}>
            <Logo />
            <HeaderButtons
                text={t('header.sign-in-button')}
            />
        </div>
    )
}

export default GuestHeader;