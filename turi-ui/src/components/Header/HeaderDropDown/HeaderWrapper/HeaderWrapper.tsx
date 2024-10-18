import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { GreyButton } from "../../../Button";
import HeaderMenu from "../HeaderMenu";
import styles from './HeaderWrapper.module.css'

const HeaderWrapper = () => {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const [isVisible, setIsVisible] = useState(false);

    const toggleDropdown = () => {
        setIsVisible(!isVisible);
    };

    const handleProfileClick = () => {
        navigate('/profile');
    };

    const handleLogoutClick = () => {
        navigate('/');
        setIsVisible(false);
    };

    return (
        <div className={styles.wrapper}>
            <GreyButton
                text={t('header.name')}
                onClick={toggleDropdown}
            />
            {isVisible && (
                <HeaderMenu
                    profileOnClick={handleProfileClick}
                    logoutOnClick={handleLogoutClick}
                />
            )}
        </div>
    )
}

export default HeaderWrapper;