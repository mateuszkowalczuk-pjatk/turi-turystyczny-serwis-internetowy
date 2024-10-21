import { ReactNode } from 'react'
import styles from './PersonalItem.module.css'

interface Props {
    index: number;
    input: ReactNode;
}

const PersonalItem = ({ index, input }: Props) => {
    return (
        <div key={index} className={styles.item}>
            {input}
        </div>
    )
}

export default PersonalItem