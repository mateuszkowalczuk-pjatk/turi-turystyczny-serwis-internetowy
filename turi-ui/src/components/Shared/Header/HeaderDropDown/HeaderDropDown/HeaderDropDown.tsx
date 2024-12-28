import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import HeaderDropDownMenu from '../HeaderDropDownMenu'
import { GreyButton } from '../../../Controls/Button'
import { authService } from '../../../../../services/authService.ts'
import { logout } from '../../../../../store/slices/auth.ts'
import styles from './HeaderDropDown.module.css'
import { useAppDispatch } from '../../../../../hooks/useAppDispatch.ts'

const HeaderDropDown = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const dispatch = useAppDispatch()
    const [isVisible, setIsVisible] = useState(false)

    const handleLogoutClick = async () => {
        const response = await authService.logout()
        switch (response.status) {
            case 200:
                dispatch(logout())
                navigate('/')
                break
            default:
                navigate('/')
                setIsVisible(!isVisible)
        }
    }

    return (
        <div className={styles.dropdown}>
            <GreyButton
                text={t('header.name')}
                onClick={() => setIsVisible(!isVisible)}
            />
            {isVisible && (
                <HeaderDropDownMenu
                    profileOnClick={() => navigate('/profile')}
                    statisticsOnClick={() => navigate('/tourism/statistics')}
                    logoutOnClick={handleLogoutClick}
                />
            )}
        </div>
    )
}

export default HeaderDropDown
