import { ReactNode } from 'react'
import SignUpPersonalInputs from '../SignUpPersonalInputs'
import styles from './SignUpPersonalPanel.module.css'

interface Props {
    label: ReactNode;
    firstInput: ReactNode;
    secondInput?: ReactNode;
    thirdInput?: ReactNode;
    fourthInput?: ReactNode;
    fifthInput?: ReactNode;
    sixthInput?: ReactNode;
}

const SignUpPersonalPanel = ({ label, firstInput, secondInput, thirdInput, fourthInput, fifthInput, sixthInput }: Props) => {
    const inputs = [firstInput, secondInput, thirdInput, fourthInput, fifthInput, sixthInput].filter(Boolean);

    return (
        <div className={styles.panel}>
            { label }
            <SignUpPersonalInputs
                inputs={ inputs }
            />
        </div>
    )
}

export default SignUpPersonalPanel;