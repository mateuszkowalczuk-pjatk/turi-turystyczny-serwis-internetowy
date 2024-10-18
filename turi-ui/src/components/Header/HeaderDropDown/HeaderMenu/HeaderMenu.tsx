import { useTranslation } from "react-i18next";
import HeaderItem from "../HeaderItem";
import styles from './HeaderMenu.module.css'

interface Props {
    profileOnClick: () => void;
    logoutOnClick: () => void;
}

const HeaderMenu = ({ profileOnClick, logoutOnClick }: Props) => {
    const { t } = useTranslation();

    return (
        <div className={styles.menu}>
            <HeaderItem
                onClick={profileOnClick}
                text={t('header.profile')}
            />
            <HeaderItem
                onClick={logoutOnClick}
                text={t('header.logout')}
            />
        </div>
    )
}

export default HeaderMenu;