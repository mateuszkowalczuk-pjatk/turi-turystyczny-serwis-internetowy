import { ReactNode } from 'react'
import PersonalItem from '../PersonalItem'
import styles from './PersonalInputs.module.css'

const PersonalInputs = ({ inputs }: { inputs: ReactNode[] }) => {
    return (
        <div className={styles.inputs}>
            {inputs.map((input, index) => (
                <PersonalItem
                    key={index}
                    index={index}
                    input={input}
                />
            ))}
        </div>
    )
}

export default PersonalInputs