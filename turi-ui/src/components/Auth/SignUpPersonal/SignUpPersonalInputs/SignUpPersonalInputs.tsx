import { ReactNode } from 'react'
import styles from './SignUpPersonalInputs.module.css'

const SignUpPersonalInputs = ({ inputs }: { inputs: ReactNode[] }) => {
    return (
        <div className={styles.inputs}>
            {inputs.map((input, index) => (
                <div key={index} className={styles.inputItem}>
                    {input}
                </div>
            ))}
        </div>
    )
}

export default SignUpPersonalInputs;