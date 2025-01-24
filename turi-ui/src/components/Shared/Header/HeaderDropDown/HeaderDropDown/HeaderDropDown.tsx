import { useHooks } from '../../../../../hooks/shared/useHooks.ts'
import { useState } from 'react'
import { GreyButton } from '../../../Controls/Button'
import HeaderDropDownMenu from '../HeaderDropDownMenu'
import { logout } from '../../../../../store/slices/auth.ts'
import { authService } from '../../../../../services/authService.ts'
import styles from './HeaderDropDown.module.css'

const HeaderDropDown = () => {
    const { t, navigate, dispatch } = useHooks()
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
                    logoutOnClick={handleLogoutClick}
                />
            )}
        </div>
    )
}

export default HeaderDropDown
