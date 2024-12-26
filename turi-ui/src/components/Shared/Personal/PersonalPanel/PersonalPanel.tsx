import { ReactNode } from 'react'
import PersonalInputs from '../PersonalInputs'
import styles from './PersonalPanel.module.css'

interface Props {
    label?: ReactNode
    firstInput: ReactNode
    secondInput?: ReactNode
    thirdInput?: ReactNode
    fourthInput?: ReactNode
    fifthInput?: ReactNode
    sixthInput?: ReactNode
    touristicPlace?: boolean
}

const PersonalPanel = ({
    label,
    firstInput,
    secondInput,
    thirdInput,
    fourthInput,
    fifthInput,
    sixthInput,
    touristicPlace
}: Props) => {
    const inputs = [firstInput, secondInput, thirdInput, fourthInput, fifthInput, sixthInput].filter(Boolean)

    return (
        <div className={`${styles.panel} ${touristicPlace ? styles.touristicPlace : ''}`}>
            {label}
            <PersonalInputs inputs={inputs} />
        </div>
    )
}

export default PersonalPanel
