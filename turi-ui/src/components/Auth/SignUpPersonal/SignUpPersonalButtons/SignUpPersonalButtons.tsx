import { useTranslation } from 'react-i18next'
import { GreenButton } from '../../../Controls/Button'
import styles from './SignUpPersonalButtons.module.css'

interface Props {
    skipOnClick: () => void;
    nextOnClick?: () => void;
}

const SignUpPersonalButtons = ({ skipOnClick, nextOnClick }: Props) => {
    const { t } = useTranslation();

    return (
        <div className={styles.buttons}>
            <GreenButton
                text={t('signup-personal.skip')}
                onClick={skipOnClick}
            />
            <GreenButton
                text={t('signup-personal.next')}
                onClick={nextOnClick}
            />
        </div>
    )
}

export default SignUpPersonalButtons;