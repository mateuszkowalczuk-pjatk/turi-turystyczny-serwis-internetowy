import { useNavigate } from 'react-router-dom'
import { GreyButton } from '../../Button'
import styles from './HeaderButtons.module.css'

interface Props {
    firstButton: string;
    secondButton: string;
}

const HeaderButtons = ({ firstButton, secondButton }: Props) => {
    const navigate = useNavigate();

    const navigateToLogin = () => {
        navigate('/login');
    };

    const navigateToSignUp = () => {
        navigate('/signup');
    };

    return (
        <div className={styles.buttons}>
            <GreyButton
                text={ firstButton }
                onClick={navigateToLogin}
            />
            <GreyButton
                text={ secondButton }
                onClick={navigateToSignUp}
            />
        </div>
    )
}

export default HeaderButtons;