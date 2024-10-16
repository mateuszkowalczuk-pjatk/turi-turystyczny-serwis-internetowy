import { ReactNode } from 'react'
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
    return (
        <div className={styles.panel}>
            { label }
            { firstInput }
            { secondInput }
            { thirdInput }
            { fourthInput }
            { fifthInput }
            { sixthInput }
        </div>
    )
}

export default SignUpPersonalPanel;