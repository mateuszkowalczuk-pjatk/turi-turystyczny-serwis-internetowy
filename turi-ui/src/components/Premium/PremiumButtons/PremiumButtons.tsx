import { ReactNode } from 'react'
import styles from './PremiumButtons.module.css'

interface Props {
    leftButton?: ReactNode
    rightButton: ReactNode
}

const PremiumButtons = ({ leftButton, rightButton }: Props) => {
    return (
        <div className={styles.buttons}>
            {leftButton}
            {rightButton}
        </div>
    )
}

export default PremiumButtons
