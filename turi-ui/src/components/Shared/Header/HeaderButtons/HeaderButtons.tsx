import { useTranslation } from 'react-i18next'
import { GreenButton, GreyButton } from '../../Controls/Button'
import HeaderDropDown from '../HeaderDropDown/HeaderDropDown'
import { usePremium } from '../../../../store/slices/premium.ts'
import { useAuthenticated } from '../../../../store/slices/auth.ts'
import styles from './HeaderButtons.module.css'

interface Props {
    text: string
    firstOnClick: () => void
    secondOnClick?: () => void
}

const HeaderButtons = ({ text, firstOnClick, secondOnClick }: Props) => {
    const { t } = useTranslation()
    const isPremium = usePremium()
    const isAuthenticated = useAuthenticated()

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
