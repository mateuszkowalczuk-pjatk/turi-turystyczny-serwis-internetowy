import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { GreyButton } from '../../../Controls/Button'
import HeaderDropDownMenu from '../HeaderDropDownMenu'
import styles from './HeaderDropDownWrapper.module.css'

const HeaderDropDownWrapper = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const [isVisible, setIsVisible] = useState(false)

    const setDropdown = () => {
        setIsVisible(!isVisible)
    }

    const handleProfileClick = () => {
        navigate('/profile')
    }

    const handleLogoutClick = () => {
        navigate('/')
    }

    return (
        <div className={styles.wrapper}>
            <GreyButton
                text={t('header.name')}
                onClick={setDropdown}
            />
            {isVisible && (
                <HeaderDropDownMenu
                    profileOnClick={handleProfileClick}
                    logoutOnClick={handleLogoutClick}
                />
            )}
        </div>
    )
}

export default HeaderDropDownWrapper
