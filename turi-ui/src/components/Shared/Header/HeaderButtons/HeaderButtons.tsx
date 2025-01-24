import { useHooks } from '../../../../hooks/shared/useHooks.ts'
import { useStates } from '../../../../hooks/shared/useStates.ts'
import { GreenButton, GreyButton } from '../../Controls/Button'
import HeaderDropDown from '../HeaderDropDown/HeaderDropDown'
import styles from './HeaderButtons.module.css'

interface Props {
    text: string
    firstOnClick: () => void
    secondOnClick?: () => void
}

const HeaderButtons = ({ text, firstOnClick, secondOnClick }: Props) => {
    const { t } = useHooks()
    const { isPremium, isAuthenticated } = useStates()

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
