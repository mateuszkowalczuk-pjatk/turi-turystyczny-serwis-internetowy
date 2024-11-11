import { useTranslation } from 'react-i18next'
import HeaderDropDownItem from '../HeaderDropDownItem'
import styles from './HeaderDropDownMenu.module.css'

interface Props {
    profileOnClick: () => void
    logoutOnClick: () => void
}

const HeaderDropDownMenu = ({ profileOnClick, logoutOnClick }: Props) => {
    const { t } = useTranslation()

    return (
        <div className={styles.menu}>
            <HeaderDropDownItem
                onClick={profileOnClick}
                text={t('header.profile')}
            />
            <HeaderDropDownItem
                onClick={logoutOnClick}
                text={t('header.logout')}
            />
        </div>
    )
}

export default HeaderDropDownMenu
