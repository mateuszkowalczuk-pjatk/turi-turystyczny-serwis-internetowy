import { useTranslation } from 'react-i18next'
import { useSelector } from 'react-redux'
import { RootState } from '../../../../store/store.ts'
import { GreenButton, GreyButton } from '../../Controls/Button'
import HeaderDropDown from '../HeaderDropDown/HeaderDropDown'
import styles from './HeaderButtons.module.css'

interface Props {
    text: string
    firstOnClick: () => void
    secondOnClick?: () => void
}

const HeaderButtons = ({ text, firstOnClick, secondOnClick }: Props) => {
    const { t } = useTranslation()
    const isPremium = useSelector((state: RootState) => state.premium.isPremiumAccount)
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated)

    return (
        <div className={styles.buttons}>
            {!isPremium && (
                <GreenButton
                    text={text}
                    onClick={firstOnClick}
                />
            )}
            {!isAuthenticated ? (
                <GreyButton
                    text={t('header.sign-up-button')}
                    onClick={secondOnClick}
                />
            ) : (
                <HeaderDropDown />
            )}
        </div>
    )
}

export default HeaderButtons
