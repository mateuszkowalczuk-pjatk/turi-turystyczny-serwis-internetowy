import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { GreyButton } from '../../../Controls/Button'
import HeaderDropDownMenu from '../HeaderDropDownMenu'
import styles from './HeaderDropDownWrapper.module.css'
import { authService } from '../../../../services/authService.ts'
import { useDispatch } from 'react-redux'
import { logout } from '../../../../store/slices/auth.ts'

const HeaderDropDownWrapper = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const [isVisible, setIsVisible] = useState(false)

    const handleLogoutClick = async () => {
        const response = await authService.logout()

        switch (response.status) {
            case 200:
                dispatch(logout())
                navigate('/')
                setIsVisible(!isVisible)
                break
            default:
                navigate('/')
        }
    }

    return (
        <div className={styles.wrapper}>
            <GreyButton
                text={t('header.name')}
                onClick={() => setIsVisible(!isVisible)}
            />
            {isVisible && (
                <HeaderDropDownMenu
                    profileOnClick={() => navigate('/profile')}
                    logoutOnClick={handleLogoutClick}
                />
            )}
        </div>
    )
}

export default HeaderDropDownWrapper
