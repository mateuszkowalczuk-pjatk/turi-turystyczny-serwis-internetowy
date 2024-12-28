import { useTranslation } from 'react-i18next'
import HeaderDropDownItem from '../HeaderDropDownItem'
import styles from './HeaderDropDownMenu.module.css'
import { usePremium } from '../../../../../store/slices/premium.ts'

interface Props {
    profileOnClick: () => void
    statisticsOnClick: () => void
    logoutOnClick: () => void
}

const HeaderDropDownMenu = ({ profileOnClick, statisticsOnClick, logoutOnClick }: Props) => {
    const { t } = useTranslation()
    const isPremium = usePremium()

    return (
        <div className={styles.menu}>
            <HeaderDropDownItem
                onClick={profileOnClick}
                text={t('header.profile')}
            />
            {isPremium && (
                <HeaderDropDownItem
                    onClick={statisticsOnClick}
                    text={t('header.statistics')}
                />
            )}
            <HeaderDropDownItem
                onClick={logoutOnClick}
                text={t('header.logout')}
            />
        </div>
    )
}

export default HeaderDropDownMenu
