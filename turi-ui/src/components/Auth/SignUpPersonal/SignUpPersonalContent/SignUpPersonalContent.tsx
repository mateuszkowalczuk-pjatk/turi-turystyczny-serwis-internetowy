import { useTranslation } from 'react-i18next'
import SignUpPersonalPart from '../SignUpPersonalPart'
import SignUpPersonalPanel from '../SignUpPersonalPanel'
import SignUpPersonalLabel from '../SignUpPersonalLabel'
import SignUpPersonalInput from '../SignUpPersonalInput'
import SignUpPersonalGender from '../SignUpPersonalGender'
import SignUpPersonalButtons from '../SignUpPersonalButtons'
import styles from './SignUpPersonalContent.module.css'

const SignUpPersonalContent = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.content}>
            <SignUpPersonalPart
                firstPanel={
                    <SignUpPersonalPanel
                        label={
                            <SignUpPersonalLabel
                                text={t('signup-personal.name-surname')}
                            />
                        }
                        firstInput={
                            <SignUpPersonalInput
                                text={t('signup-personal.name')}
                            />
                        }
                        secondInput={
                            <SignUpPersonalInput
                                text={t('signup-personal.surname')}
                            />
                        }
                    />
                }
                secondPanel={
                    <SignUpPersonalPanel
                        label={
                            <SignUpPersonalLabel
                                text={t('signup-personal.birthdate')}
                            />
                        }
                        firstInput={
                            <SignUpPersonalInput
                                text={t('signup-personal.day')}
                            />
                        }
                        secondInput={
                            <SignUpPersonalInput
                                text={t('signup-personal.month')}
                            />
                        }
                        thirdInput={
                            <SignUpPersonalInput
                                text={t('signup-personal.year')}
                            />
                        }
                    />
                }
                option={
                    <SignUpPersonalPanel
                        label={
                            <SignUpPersonalLabel
                                text={t('signup-personal.gender')}
                            />
                        }
                        firstInput={
                            <SignUpPersonalGender />
                        }
                    />
                }
            />
            <SignUpPersonalPart
                firstPanel={
                    <SignUpPersonalPanel
                        label={
                            <SignUpPersonalLabel
                                text={t('signup-personal.phone-number')}
                            />
                        }
                        firstInput={
                            <SignUpPersonalInput
                                text={t('signup-personal.phone-number')}
                            />
                        }
                    />
                }
                secondPanel={
                    <SignUpPersonalPanel
                        label={
                            <SignUpPersonalLabel
                                text={t('signup-personal.address')}
                            />
                        }
                        firstInput={
                            <SignUpPersonalInput
                                text={t('signup-personal.country')}
                            />
                        }
                        secondInput={
                            <SignUpPersonalInput
                                text={t('signup-personal.city')}
                            />
                        }
                        thirdInput={
                            <SignUpPersonalInput
                                text={t('signup-personal.zipcode')}
                            />
                        }
                        fourthInput={
                            <SignUpPersonalInput
                                text={t('signup-personal.street')}
                            />
                        }
                        fifthInput={
                            <SignUpPersonalInput
                                text={t('signup-personal.building')}
                            />
                        }
                        sixthInput={
                            <SignUpPersonalInput
                                text={t('signup-personal.apartment')}
                            />
                        }
                    />
                }
                option={
                    <SignUpPersonalButtons />
                }
            />
      </div>
    )
}

export default SignUpPersonalContent;